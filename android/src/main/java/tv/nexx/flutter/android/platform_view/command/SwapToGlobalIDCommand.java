package tv.nexx.flutter.android.platform_view.command;

import java.util.Objects;

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
        final String globalID = Objects.requireNonNull(payload.call().argument("globalID"), "Expected argument was not received");
        final Integer startPosition = Objects.requireNonNull(payload.call().argument("startPosition"), "Expected argument was not received");
        final Double delay = Objects.requireNonNull(payload.call().argument("delay"), "Expected argument was not received");
        receiver.state().player().swapToGlobalID(globalID, startPosition, delay);
        payload.result().success(NexxPlayMethodResult.from(receiver.state().id().numeric()).asMap());
    }
}
