package tv.nexx.flutter.android.platform_view.command;

import com.google.android.gms.cast.framework.CastContext;

import java.util.HashMap;

import tv.nexx.android.play.NexxPLAYConfiguration;
import tv.nexx.flutter.android.estd.functional.Either;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformViewState;

class StartPlayerCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final StartPlayerCommand INSTANCE = new StartPlayerCommand();
    private static final NexxPlayStateDispatchTable PLAYER_DISPATCH_TABLE = NexxPlayStateDispatchTable.get();

    private StartPlayerCommand() {
    }

    public static StartPlayerCommand create() {
        return INSTANCE;
    }

    @SuppressWarnings({"ConstantConditions"})
    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final NexxPlayPlatformViewState state = receiver.state();
        final NexxPlayPlaybackConfiguration playback = NexxPlayPlaybackConfiguration.from(payload.call().argument("playback"));
        final NexxPLAYConfiguration configuration = new NexxPLAYConfiguration(new HashMap<>(payload.call().argument("configuration")));
        final NexxPlayPlaybackPayload dispatchPayload = NexxPlayPlaybackPayload.of(configuration, playback);
        PLAYER_DISPATCH_TABLE.dispatch(playback.mediaSourceType(), state.player(), dispatchPayload);
        final Either<Exception, CastContext> context = state.castContext();
        passResult(payload, state, context);
    }

    private void passResult(NexxPlayDispatchPayload payload,
                            NexxPlayPlatformViewState state,
                            Either<Exception, CastContext> context) {
        if (context == null) {
            payload.result().success(NexxPlayMethodResult.from(state.id().numeric()).asMap());
        } else {
            final NexxPlayMethodResult result = context.fold(
                    e -> NexxPlayMethodResult.from(state.id().numeric())
                            .put("chrome_cast_enabled", false)
                            .put("chrome_cast_failure_cause", e.getMessage()),
                    ctx -> NexxPlayMethodResult.from(state.id().numeric())
                            .put("chrome_cast_enabled", true)
            );
            payload.result().success(result.asMap());
        }
    }
}
