package tv.nexx.flutter.android.platform_view.command;

import java.util.HashMap;

import tv.nexx.android.play.NexxPLAYConfiguration;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformViewState;

class StartPlayerCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final NexxPlayStateDispatchTable PLAYER_DISPATCH_TABLE = NexxPlayStateDispatchTable.get();

    private StartPlayerCommand() {
    }

    public static StartPlayerCommand create() {
        return new StartPlayerCommand();
    }

    @SuppressWarnings({"ConstantConditions"})
    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final NexxPlayPlatformViewState state = receiver.state();
        final NexxPlayPlaybackConfiguration playback = NexxPlayPlaybackConfiguration.from(payload.call().argument("playback"));
        final NexxPLAYConfiguration configuration = new NexxPLAYConfiguration(new HashMap<>(payload.call().argument("configuration")));
        final NexxPlayPlaybackPayload dispatchPayload = NexxPlayPlaybackPayload.of(configuration, playback);
        PLAYER_DISPATCH_TABLE.dispatch(playback.mediaSourceType(), state.player(), dispatchPayload);
        payload.result().success(NexxPlayMethodResult.from(state.id().numeric()).asMap());
    }

}
