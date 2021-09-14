package tv.nexx.flutter.android.platform_view;

public final class UndefinedMethodException extends IllegalArgumentException {
    public UndefinedMethodException(String method) {
        super("Undefined method: " + method);
    }
}
