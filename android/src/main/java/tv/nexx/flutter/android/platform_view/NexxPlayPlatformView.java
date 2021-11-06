package tv.nexx.flutter.android.platform_view;

import android.content.res.Configuration;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;
import tv.nexx.android.play.NexxPLAYNotification;
import tv.nexx.android.play.player.Player;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.android.event.AndroidEventVisitor;
import tv.nexx.flutter.android.android.event.OnPIPModeChangedEvent;
import tv.nexx.flutter.android.android.event.OnUserLeaveHintEvent;
import tv.nexx.flutter.android.estd.functional.Consumer;
import tv.nexx.flutter.android.estd.virtual_dispatch.UndefinedDispatchTableMethodException;

final class NexxPlayPlatformView implements PlatformView, MethodChannel.MethodCallHandler,
        EventChannel.StreamHandler, LifecycleObserver, NoOpNexxPlayListener,
        Consumer<AndroidEvent>, AndroidEventVisitor {

    private static final NexxPlayPlatformViewDispatchTable DISPATCH_TABLE = NexxPlayPlatformViewDispatchTable.get();
    private static final NexxPlayStateDispatchTable PLAYER_DISPATCH_TABLE = NexxPlayStateDispatchTable.get();

    private final NexxPlayPlatformViewState state;

    NexxPlayPlatformView(NexxPlayPlatformViewState state) {
        this.state = state;
    }

    void initialize() {
        state.initialize(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        try {
            DISPATCH_TABLE.dispatch(call.method, this, new NexxPlayDispatchPayload(call, result));
        } catch (UndefinedDispatchTableMethodException unused) {
            result.notImplemented();
        }
    }

    void start(NexxPlayDispatchPayload payload) {
        if (state.player() == null) return;
        final NexxPlayInitializationArguments args = state.initializationArguments();
        state.player().setEnvironment(args.nexxPLAYEnvironment());
        final NexxPlayPlaybackConfiguration playback = NexxPlayPlaybackConfiguration.from(payload.call().arguments());
        PLAYER_DISPATCH_TABLE.dispatch(playback.mediaSourceType(), state.player(), NexxPlayPlaybackPayload.of(args.nexxPLAYConfiguration(), playback));
        payload.result().success(StartCallResult.started(state.id()).asMap());
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        if (state.isDisposed()) return;
        state.sink(events);
        state.player().addPlaystateListener(this);
    }

    @Override
    public void onCancel(Object arguments) {
        if (state.isDisposed()) return;
        state.player().removePlaystateListener(this);
        state.sink(null);
    }

    @Override
    public void onPlayerStateChanged(boolean pwr, Player.State current) {
        final EventChannel.EventSink sink = state.sink();
        if (sink != null) sink.success(PlayerStateChangeEvent.of(state.id(), pwr, current).asMap());
    }

    @Override
    public void onPlayerEvent(NexxPLAYNotification.IPlayerEvent e) {
        final EventChannel.EventSink sink = state.sink();
        if (sink != null) sink.success(DirectPlayerEvent.of(state.id(), e).asMap());
    }

    @Override
    public void accept(AndroidEvent value) {
        value.visit(this);
    }

    @Override
    public void visit(OnUserLeaveHintEvent event) {
        if (!state.isDisposed()) state.player().onUserLeaveHint();
    }

    @Override
    public void visit(OnPIPModeChangedEvent event) {
        final boolean isPIP = event.isInPictureInPictureMode();
        final Configuration config = event.getNewConfig();
        if (!state.isDisposed()) state.player().onPictureInPictureModeChanged(isPIP, config);
    }

    @Override
    public View getView() {
        return state.host();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (!state.isDisposed()) state.player().onActivityResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if (!state.isDisposed()) state.player().onActivityPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if (!state.isDisposed()) state.player().onActivityDestroyed();
    }

    @Override
    public void dispose() {
        state.dispose(this);
    }
}
