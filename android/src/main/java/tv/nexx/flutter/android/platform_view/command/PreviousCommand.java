package tv.nexx.flutter.android.platform_view.command;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class PreviousCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final PreviousCommand INSTANCE = new PreviousCommand();

    private PreviousCommand() {
    }

    public static PreviousCommand create() {
        return INSTANCE;
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        receiver.state().player().previous();
        payload.result().success(NexxPlayMethodResult.from(receiver.state().id().numeric()).asMap());
    }
}
