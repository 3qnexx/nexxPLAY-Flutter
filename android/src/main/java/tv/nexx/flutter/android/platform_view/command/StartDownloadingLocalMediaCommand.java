package tv.nexx.flutter.android.platform_view.command;

import java.util.Objects;

import io.flutter.plugin.common.MethodCall;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

public class StartDownloadingLocalMediaCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private StartDownloadingLocalMediaCommand() {
    }

    public static StartDownloadingLocalMediaCommand create() {
        return new StartDownloadingLocalMediaCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final MethodCall call = payload.call();
        final String mediaID = Objects.requireNonNull(call.argument("mediaID"), "Expected argument was not received");
        final String streamType = Objects.requireNonNull(call.argument("streamType"), "Expected argument was not received");
        final String provider = call.argument("provider");
        receiver.state().player().startDownloadLocalMedia(mediaID, streamType, provider);
        payload.result().success(NexxPlayMethodResult.from(receiver.state().id().numeric()).asMap());
    }
}
