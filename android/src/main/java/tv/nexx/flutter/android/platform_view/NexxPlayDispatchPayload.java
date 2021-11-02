package tv.nexx.flutter.android.platform_view;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public final class NexxPlayDispatchPayload {
    private final MethodCall call;
    private final MethodChannel.Result result;

    public NexxPlayDispatchPayload(MethodCall call, MethodChannel.Result result) {
        this.call = call;
        this.result = result;
    }

    @SuppressWarnings("unused")
    public MethodCall call() {
        return call;
    }

    public MethodChannel.Result result() {
        return result;
    }
}
