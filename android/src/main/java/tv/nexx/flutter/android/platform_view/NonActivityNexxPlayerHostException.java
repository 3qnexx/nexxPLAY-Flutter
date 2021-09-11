package tv.nexx.flutter.android.platform_view;

public final class NonActivityNexxPlayerHostException extends IllegalArgumentException {
    public NonActivityNexxPlayerHostException() {
        super("Nexx Player only supports activities as hosts as it interacts with Window directly.");
    }
}
