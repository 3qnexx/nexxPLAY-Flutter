package tv.nexx.flutter.android.platform_view.command;

import java.util.HashMap;
 import java.util.Map;
import java.util.Objects;

import tv.nexx.android.play.NexxPLAYConfiguration;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformViewState;

class StartPlayerCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final NexxPlayStateDispatchTable PLAYER_DISPATCH_TABLE = NexxPlayStateDispatchTable.get();
    private final Map<String, Object> configuration;

    private StartPlayerCommand(Map<String, Object> configuration) {
        this.configuration = Objects.requireNonNull(configuration);
    }

    public static StartPlayerCommand create(Map<String, Object> configuration) {
        return new StartPlayerCommand(configuration);
    }

    @SuppressWarnings({"ConstantConditions"})
    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final NexxPlayPlatformViewState state = receiver.state();
        final NexxPlayPlaybackConfiguration playback = NexxPlayPlaybackConfiguration.from(payload.call().argument("playback"));
        final Map<String, Object> mergedConfiguration = new HashMap<>(configuration);
        mergedConfiguration.putAll(payload.call().argument("configuration"));
        final NexxPLAYConfiguration configuration = new NexxPLAYConfiguration(mergedConfiguration);
        final NexxPlayPlaybackPayload dispatchPayload = NexxPlayPlaybackPayload.of(configuration, playback);
        PLAYER_DISPATCH_TABLE.dispatch(playback.mediaSourceType(), state.player(), dispatchPayload);
        payload.result().success(NexxPlayMethodResult.from(state.id().numeric()).asMap());
    }

}
