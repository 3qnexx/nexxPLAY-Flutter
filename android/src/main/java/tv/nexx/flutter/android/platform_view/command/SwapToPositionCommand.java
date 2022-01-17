package tv.nexx.flutter.android.platform_view.command;

import java.util.Objects;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class SwapToPositionCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private SwapToPositionCommand() {
    }

    public static SwapToPositionCommand create() {
        return new SwapToPositionCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final Integer position = Objects.requireNonNull(payload.call().argument("position"), "Expected argument was not received");
        receiver.state().player().swapToPosition(position);
        payload.result().success(NexxPlayMethodResult.from(receiver.state().id().numeric()).asMap());
    }
}
