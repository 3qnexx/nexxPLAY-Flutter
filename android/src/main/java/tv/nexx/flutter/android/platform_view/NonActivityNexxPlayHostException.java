package tv.nexx.flutter.android.platform_view;

public final class NonActivityNexxPlayHostException extends IllegalArgumentException {
    public NonActivityNexxPlayHostException() {
        super("nexxPLAY only supports activities as hosts as it interacts with Window directly.");
    }
}
