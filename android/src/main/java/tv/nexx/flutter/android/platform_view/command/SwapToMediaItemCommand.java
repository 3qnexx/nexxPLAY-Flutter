package tv.nexx.flutter.android.platform_view.command;

import java.util.Objects;

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
        final String mediaID = Objects.requireNonNull(payload.call().argument("mediaID"), "Expected argument was not received");
        final String streamType = payload.call().argument("streamType");
        final Integer startPosition = Objects.requireNonNull(payload.call().argument("startPosition"), "Expected argument was not received");
        final Double delay = Objects.requireNonNull(payload.call().argument("delay"), "Expected argument was not received");
        receiver.state().player().swapToMediaItem(mediaID, streamType, startPosition, delay);
        payload.result().success(NexxPlayMethodResult.from(receiver.state().id().numeric()).asMap());
    }
}
