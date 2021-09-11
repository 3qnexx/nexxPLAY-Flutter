package tv.nexx.flutter.android.platform_view;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.Objects;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;
import tv.nexx.android.play.INexxPLAY;
import tv.nexx.android.play.MediaSourceType;
import tv.nexx.android.play.NexxPLAY;
import tv.nexx.flutter.android.NexxPluginEnvironment;
import tv.nexx.flutter.android.estd.functional.Supplier;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTable;
import tv.nexx.flutter.android.estd.virtual_dispatch.UndefinedDispatchTableMethodException;
import tv.nexx.flutter.android.nexx_player.NexxPlayerConfiguration;

final class NexxPlayerPlatformView implements PlatformView,
        MethodChannel.MethodCallHandler,
        LifecycleObserver {

    private static final DispatchTable<String, NexxPlayerPlatformView, NexxPlayerDispatchPayload> DISPATCH_TABLE = DispatchTable.threadConfined();
    private static final DispatchTable<MediaSourceType, INexxPLAY, NexxPlayerConfiguration> PLAYER_DISPATCH_TABLE = DispatchTable.threadConfined();

    static {
        DISPATCH_TABLE.set("test", NexxPlayerPlatformView::test);
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
    private final Supplier<Lifecycle> lifecycle;
    private final NexxPlayerConfiguration configuration;
    // Mutable due to the PlatformView#dispose contract
    @Nullable
    private ViewGroup host;
    @Nullable
    private INexxPLAY player;

    private NexxPlayerPlatformView(int id,
                                   NexxPlayerConfiguration configuration,
                                   Supplier<Lifecycle> lifecycle,
                                   ViewGroup host,
                                   INexxPLAY player,
                                   MethodChannel methodChannel) {
        this.id = id;
        this.configuration = Objects.requireNonNull(configuration);
        this.lifecycle = Objects.requireNonNull(lifecycle);
        this.methodChannel = Objects.requireNonNull(methodChannel);
        this.host = Objects.requireNonNull(host);
        this.player = Objects.requireNonNull(player);
    }

    // Method exists to prevent object leaking in the constructor
    // https://stackoverflow.com/a/9851843/7884542
    private void runBindings() {
        final Lifecycle lifecycle = this.lifecycle.get();
        Objects.requireNonNull(lifecycle, "Lifecycle is null, normal operation is disrupted.");
        methodChannel.setMethodCallHandler(this);
        lifecycle.addObserver(this);
    }

    public static NexxPlayerPlatformView create(int id,
                                                NexxPlayerConfiguration configuration,
                                                Activity activity,
                                                Supplier<Lifecycle> lifecycle,
                                                BinaryMessenger messenger) {
        final ViewGroup host = createPlayerHostView(activity);
        final INexxPLAY player = createPlayer(activity, host);
        final MethodChannel channel = createMethodChannel(messenger, id);
        final NexxPlayerPlatformView view =
                new NexxPlayerPlatformView(id, configuration, lifecycle, host, player, channel);
        view.runBindings();
        return view;
    }

    private static ViewGroup createPlayerHostView(Context context) {
        final FrameLayout root = new FrameLayout(context);
        root.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        return root;
    }

    @NonNull
    private static NexxPLAY createPlayer(Activity activity, ViewGroup host) {
        return new NexxPLAY(activity, host, activity.getWindow());
    }

    private static MethodChannel createMethodChannel(BinaryMessenger messenger, int id) {
        return new MethodChannel(messenger, NexxPluginEnvironment.PLUGIN_IDENTIFIER + "_" + id);
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
        host = null;
        player = null;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        try {
            DISPATCH_TABLE.dispatch(call.method, this, new NexxPlayerDispatchPayload(call, result));
        } catch (UndefinedDispatchTableMethodException unused) {
            result.notImplemented();
        }
    }

    private void test(NexxPlayerDispatchPayload payload) {
        payload.result().success(NexxPlayerMethodResult.from(id).put("test", "Heyoo").asMap());
    }

    private void start(NexxPlayerDispatchPayload payload) {
        if (player == null) return;
        player.setEnvironment(configuration.nexxPLAYEnvironment());
        PLAYER_DISPATCH_TABLE.dispatch(configuration.getMediaSourceType(), player, configuration);
        payload.result().success(NexxPlayerMethodResult.from(id).put("started", true).asMap());
    }

}
