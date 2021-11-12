package tv.nexx.flutter.android.platform_view.command;

import java.util.Objects;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class SeekByCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final SeekByCommand INSTANCE = new SeekByCommand();

    private SeekByCommand() {
    }

    public static SeekByCommand create() {
        return INSTANCE;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final Double time = Objects.requireNonNull(payload.call().argument("time"), "Expected argument was not received");
        receiver.state().player().seekBy(time.floatValue());
        payload.result().success(NexxPlayMethodResult.from(receiver.state().id().numeric()).asMap());
    }
}
