package tv.nexx.flutter.android.platform_view.command;

import java.util.Objects;

import io.flutter.plugin.common.MethodCall;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class SwapToMediaItemCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final SwapToMediaItemCommand INSTANCE = new SwapToMediaItemCommand();

    private SwapToMediaItemCommand() {
    }

    public static SwapToMediaItemCommand create() {
        return INSTANCE;
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final MethodCall call = payload.call();
        final String mediaID = Objects.requireNonNull(call.argument("mediaID"), "Expected argument was not received");
        final String streamType = call.argument("streamType");
        final Integer startPosition = Objects.requireNonNull(call.argument("startPosition"), "Expected argument was not received");
        final Double delay = Objects.requireNonNull(call.argument("delay"), "Expected argument was not received");
        final String reason = Objects.requireNonNull(call.argument("reason"), "Expected argument was not received");
        final Boolean showReturnButton = Objects.requireNonNull(call.argument("showReturnButton"), "Expected argument was not received");
        receiver.state().player().swapToMediaItem(mediaID, streamType, startPosition, delay, reason, showReturnButton);
        payload.result().success(NexxPlayMethodResult.from(receiver.state().id().numeric()).asMap());
    }
}
