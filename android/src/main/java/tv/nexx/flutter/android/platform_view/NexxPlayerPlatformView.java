package tv.nexx.flutter.android.platform_view;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;
import tv.nexx.android.play.Event;
import tv.nexx.android.play.INexxPLAY;
import tv.nexx.android.play.MediaSourceType;
import tv.nexx.android.play.NexxPLAY;
import tv.nexx.android.play.NexxPLAYNotification;
import tv.nexx.android.play.PlayerEvent;
import tv.nexx.android.play.player.Player;
import tv.nexx.flutter.android.NexxPluginEnvironment;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.android.event.AndroidEventVisitor;
import tv.nexx.flutter.android.android.event.OnPIPModeChangedEvent;
import tv.nexx.flutter.android.android.event.OnUserLeaveHintEvent;
import tv.nexx.flutter.android.estd.functional.Consumer;
import tv.nexx.flutter.android.estd.functional.Supplier;
import tv.nexx.flutter.android.estd.observer.Subject;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTable;
import tv.nexx.flutter.android.estd.virtual_dispatch.UndefinedDispatchTableMethodException;
import tv.nexx.flutter.android.nexx_player.NexxPlayerConfiguration;

final class NexxPlayerPlatformView implements PlatformView,
        MethodChannel.MethodCallHandler,
        LifecycleObserver,
        EventChannel.StreamHandler,
        NexxPLAYNotification.Listener,
        Consumer<AndroidEvent>,
        AndroidEventVisitor {

    private static final DispatchTable<String, NexxPlayerPlatformView, NexxPlayerDispatchPayload> DISPATCH_TABLE = DispatchTable.threadConfined();
    private static final DispatchTable<MediaSourceType, INexxPLAY, NexxPlayerConfiguration> PLAYER_DISPATCH_TABLE = DispatchTable.threadConfined();

    static {
        DISPATCH_TABLE.set("start", NexxPlayerPlatformView::start);
        PLAYER_DISPATCH_TABLE.set(MediaSourceType.NORMAL, (player, config) ->
                player.startPlay(config.getPlayMode(), config.getMediaID(), config.nexxPLAYConfiguration()));
        PLAYER_DISPATCH_TABLE.set(MediaSourceType.REMOTE, (player, config) ->
                player.startPlayWithRemoteMedia(config.getPlayMode(), config.getMediaID(), config.getProvider(), config.nexxPLAYConfiguration()));
        PLAYER_DISPATCH_TABLE.set(MediaSourceType.GLOBAL, (player, config) ->
                player.startPlayWithGlobalID(config.getMediaID(), config.nexxPLAYConfiguration()));
    }

    private final int id;
    private final MethodChannel methodChannel;
    private final EventChannel eventChannel;
    private final Supplier<Lifecycle> lifecycle;
    private final NexxPlayerConfiguration configuration;
    private final Subject<AndroidEvent> subject;
    // Mutable due to the PlatformView#dispose contract
    @Nullable
    private EventChannel.EventSink sink;
    @Nullable
    private ViewGroup host;
    @Nullable
    private INexxPLAY player;

    private NexxPlayerPlatformView(Supplier<Lifecycle> lifecycle,
                                   Subject<AndroidEvent> subject,
                                   int id,
                                   NexxPlayerConfiguration configuration,
                                   ViewGroup host,
                                   INexxPLAY player,
                                   MethodChannel methodChannel,
                                   EventChannel eventChannel) {
        this.id = id;
        this.configuration = Objects.requireNonNull(configuration);
        this.lifecycle = Objects.requireNonNull(lifecycle);
        this.methodChannel = Objects.requireNonNull(methodChannel);
        this.eventChannel = eventChannel;
        this.host = Objects.requireNonNull(host);
        this.player = Objects.requireNonNull(player);
        this.subject = Objects.requireNonNull(subject);
    }

    // Method exists to prevent object leaking in the constructor
    // https://stackoverflow.com/a/9851843/7884542
    private void runBindings() {
        final Lifecycle lifecycle = this.lifecycle.get();
        Objects.requireNonNull(lifecycle, "Lifecycle is null, normal operation is disrupted.");
        methodChannel.setMethodCallHandler(this);
        eventChannel.setStreamHandler(this);
        lifecycle.addObserver(this);
        subject.subscribe(this);
    }

    public static NexxPlayerPlatformView create(BinaryMessenger messenger,
                                                Supplier<Lifecycle> lifecycle,
                                                Subject<AndroidEvent> subject,
                                                Activity activity,
                                                int id,
                                                NexxPlayerConfiguration configuration) {
        final String identifier = instanceIdentifier(id);
        final NexxPlayerViewHost host = createPlayerHostView(activity);
        final INexxPLAY player = new NexxPLAY(activity, host.getPlayerArea(), activity.getWindow());
        final MethodChannel methodChannel = new MethodChannel(messenger, identifier + "_methods");
        final EventChannel eventChannel = new EventChannel(messenger, identifier + "_events");
        final NexxPlayerPlatformView view = new NexxPlayerPlatformView(lifecycle, subject, id,
                configuration, host.getRoot(), player, methodChannel, eventChannel);
        view.runBindings();
        return view;
    }

    private static NexxPlayerViewHost createPlayerHostView(Context context) {
        final RelativeLayout root = new RelativeLayout(context);
        root.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        root.setBackgroundColor(Color.BLACK);
        final FrameLayout playerArea = new FrameLayout(context);
        playerArea.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        root.addView(playerArea);
        return new NexxPlayerViewHost(root, playerArea);
    }

    @NonNull
    private static String instanceIdentifier(int id) {
        return NexxPluginEnvironment.PLUGIN_IDENTIFIER + "_" + id;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        try {
            DISPATCH_TABLE.dispatch(call.method, this, new NexxPlayerDispatchPayload(call, result));
        } catch (UndefinedDispatchTableMethodException unused) {
            result.notImplemented();
        }
    }

    private void start(NexxPlayerDispatchPayload payload) {
        if (player == null) return;
        player.setEnvironment(configuration.nexxPLAYEnvironment());
        PLAYER_DISPATCH_TABLE.dispatch(configuration.getMediaSourceType(), player, configuration);
        payload.result().success(NexxPlayerMethodResult.from(id).put("started", true).asMap());
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        if (player == null) return;
        this.sink = events;
        player.addPlaystateListener(this);
    }

    @Override
    public void onCancel(Object arguments) {
        if (player == null) return;
        player.removePlaystateListener(this);
        this.sink = null;
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, Player.State current) {
        final EventChannel.EventSink sink = this.sink;
        if (sink != null) {
            final Map<String, Object> result = NexxPlayerMethodResult.from(id)
                    .put("player_event_type", "player_state_changed")
                    .put("play_when_ready", playWhenReady)
                    .put("state", current.name())
                    .asMap();
            sink.success(result);
        }
    }

    @Override
    public void onPlayerEvent(NexxPLAYNotification.IPlayerEvent e) {
        final EventChannel.EventSink sink = this.sink;
        if (sink != null) {
            final Map<String, Object> body = preprocessEventBody(e);
            final Map<String, Object> result = NexxPlayerMethodResult.from(id)
                    .put("properties", body)
                    .put("player_event_type", "player_event")
                    .asMap();
            sink.success(result);
        }
    }

    @NonNull
    private Map<String, Object> preprocessEventBody(NexxPLAYNotification.IPlayerEvent e) {
        final Map<String, Object> body = new HashMap<>(e.getBody());
        final Object event = body.get(PlayerEvent.EVENT);
        if (event != null) {
            body.put(PlayerEvent.EVENT, ((Event) event).name());
        }
        return body;
    }

    @Override
    public void onFullScreen(boolean fullScreen) {
        // This method isn't even used by the player
    }

    @Override
    public void onPlayerError(String reason, String details) {
        // No-op
    }

    @Override
    public void onVideoSizeChanged(int width, int height, float pixelWidthAspectRatio) {
        // No-op
    }

    @Override
    public void accept(AndroidEvent value) {
        value.visit(this);
    }

    @Override
    public void visit(OnUserLeaveHintEvent event) {
        if (player != null) player.onUserLeaveHint();
    }

    @Override
    public void visit(OnPIPModeChangedEvent event) {
        if (player != null) {
            final boolean isPIP = event.isInPictureInPictureMode();
            player.onPictureInPictureModeChanged(isPIP, event.getNewConfig());
        }
    }

    @Override
    public View getView() {
        return host;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (player != null) player.onActivityResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if (player != null) player.onActivityPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if (player != null) player.onActivityDestroyed();
    }

    @Override
    public void dispose() {
        final Lifecycle lifecycle = this.lifecycle.get();
        if (lifecycle != null) lifecycle.removeObserver(this);
        methodChannel.setMethodCallHandler(null);
        eventChannel.setStreamHandler(null);
        subject.unsubscribe(this);
        sink = null;
        if (player != null) {
            player.removePlaystateListener(this);
            player.onActivityDestroyed();
        }
        player = null;
        host = null;
    }
}
