package tv.nexx.flutter.android.platform_view.command;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class IsCastingCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private IsCastingCommand() {
    }

    public static IsCastingCommand create() {
        return new IsCastingCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final boolean isCasting = receiver.state().player().isCasting();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id).put("result", isCasting);
        payload.result().success(result.asMap());
    }

}
