package tv.nexx.flutter.android.platform_view.command;

import java.util.Objects;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class SwapToRemoteMediaCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final SwapToRemoteMediaCommand INSTANCE = new SwapToRemoteMediaCommand();

    private SwapToRemoteMediaCommand() {
    }

    public static SwapToRemoteMediaCommand create() {
        return INSTANCE;
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final String reference = Objects.requireNonNull(payload.call().argument("reference"), "Expected argument was not received");
        final String provider = Objects.requireNonNull(payload.call().argument("provider"), "Expected argument was not received");
        final Double delay = Objects.requireNonNull(payload.call().argument("delay"), "Expected argument was not received");
        receiver.state().player().swapToRemoteMedia(reference, provider, delay);
        payload.result().success(NexxPlayMethodResult.from(receiver.state().id().numeric()).asMap());
    }
}
