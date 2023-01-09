package tv.nexx.flutter.android.platform_view;

import android.view.View;

import androidx.annotation.NonNull;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;
import tv.nexx.android.play.NexxPLAYNotification;
import tv.nexx.android.play.player.IPlayer;
import tv.nexx.flutter.android.estd.virtual_dispatch.UndefinedDispatchTableMethodException;
import tv.nexx.flutter.android.platform_view.command.NexxPlayPlatformViewDispatchTable;

public final class NexxPlayPlatformView implements PlatformView, MethodChannel.MethodCallHandler,
        EventChannel.StreamHandler, NoOpNexxPlayListener {

    private static final NexxPlayPlatformViewDispatchTable DISPATCH_TABLE = NexxPlayPlatformViewDispatchTable.get();

    private final NexxPlayPlatformViewState state;

    NexxPlayPlatformView(NexxPlayPlatformViewState state) {
        this.state = state;
    }

    void initialize() {
        state.initialize(this);
        state.player().setEnvironment(state.environment());
    }

    public NexxPlayPlatformViewState state() {
        return state;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        try {
            DISPATCH_TABLE.dispatch(call.method, this, new NexxPlayDispatchPayload(call, result));
        } catch (UndefinedDispatchTableMethodException unused) {
            result.notImplemented();
        }
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        if (state.isDisposed()) {
            return;
        }
        state.sink(events);
        state.player().addPlaystateListener(this);
    }

    @Override
    public void onCancel(Object arguments) {
        if (state.isDisposed()) {
            return;
        }
        state.player().removePlaystateListener(this);
        state.sink(null);
    }

    @Override
    public void onPlayerStateChanged(IPlayer.State current) {
        state.sink().success(PlayerStateChangeEvent.of(state.id(), current).asMap());
    }

    @Override
    public void onPlayerEvent(NexxPLAYNotification.IPlayerEvent e) {
        state.sink().success(DirectPlayerEvent.of(state.id(), e).asMap());
    }

    @Override
    public View getView() {
        return state.host();
    }

    @Override
    public void dispose() {
        state.dispose(this);
    }
}
