package tv.nexx.flutter.android.platform_view.command;

import java.util.Objects;

import io.flutter.plugin.common.MethodCall;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class HasDownloadOfLocalMediaCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final HasDownloadOfLocalMediaCommand INSTANCE = new HasDownloadOfLocalMediaCommand();

    private HasDownloadOfLocalMediaCommand() {
    }

    public static HasDownloadOfLocalMediaCommand create() {
        return INSTANCE;
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final MethodCall call = payload.call();
        final String mediaID = Objects.requireNonNull(call.argument("mediaID"), "Expected argument was not received");
        final String streamType = Objects.requireNonNull(call.argument("streamType"), "Expected argument was not received");
        final String provider = call.argument("provider");
        final boolean hasDownload = receiver.state().player().hasDownloadOfLocalMedia(mediaID, streamType, provider);
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id).put("result", hasDownload);
        payload.result().success(result.asMap());
    }

}
