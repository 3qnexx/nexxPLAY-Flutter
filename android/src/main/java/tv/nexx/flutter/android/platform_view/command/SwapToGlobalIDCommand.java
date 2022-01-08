package tv.nexx.flutter.android.platform_view.command;

import java.util.Objects;

import io.flutter.plugin.common.MethodCall;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class SwapToGlobalIDCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final SwapToGlobalIDCommand INSTANCE = new SwapToGlobalIDCommand();

    private SwapToGlobalIDCommand() {
    }

    public static SwapToGlobalIDCommand create() {
        return INSTANCE;
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final MethodCall call = payload.call();
        final String globalID = Objects.requireNonNull(call.argument("globalID"), "Expected argument was not received");
        final Integer startPosition = Objects.requireNonNull(call.argument("startPosition"), "Expected argument was not received");
        final Double delay = Objects.requireNonNull(call.argument("delay"), "Expected argument was not received");
        final String reason = Objects.requireNonNull(call.argument("reason"), "Expected argument was not received");
        final Boolean showReturnButton = Objects.requireNonNull(call.argument("showReturnButton"), "Expected argument was not received");
        receiver.state().player().swapToGlobalID(globalID, startPosition, delay, reason, showReturnButton);
        payload.result().success(NexxPlayMethodResult.from(receiver.state().id().numeric()).asMap());
    }
}
