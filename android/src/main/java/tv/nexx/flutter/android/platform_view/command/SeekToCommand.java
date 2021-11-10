package tv.nexx.flutter.android.platform_view.command;

import java.util.Objects;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class SeekToCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final SeekToCommand INSTANCE = new SeekToCommand();

    private SeekToCommand() {
    }

    public static SeekToCommand create() {
        return INSTANCE;
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final Double time = Objects.requireNonNull(payload.call().argument("time"), "Expected argument was not received");
        receiver.state().player().seekTo(time.floatValue());
        payload.result().success(NexxPlayMethodResult.from(receiver.state().id().numeric()).asMap());
    }
}
