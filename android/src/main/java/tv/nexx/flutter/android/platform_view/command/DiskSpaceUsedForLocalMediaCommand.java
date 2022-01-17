package tv.nexx.flutter.android.platform_view.command;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class DiskSpaceUsedForLocalMediaCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private DiskSpaceUsedForLocalMediaCommand() {
    }

    public static DiskSpaceUsedForLocalMediaCommand create() {
        return new DiskSpaceUsedForLocalMediaCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final long space = receiver.state().player().diskSpaceUsedForLocalMedia();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id).put("result", space);
        payload.result().success(result.asMap());
    }

}
