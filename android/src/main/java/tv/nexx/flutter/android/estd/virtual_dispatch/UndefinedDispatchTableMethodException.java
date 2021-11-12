package tv.nexx.flutter.android.estd.virtual_dispatch;

public class UndefinedDispatchTableMethodException extends IllegalStateException {
    public UndefinedDispatchTableMethodException(Object identifier) {
        super("Method with the next identifier was not defined: " + identifier);
    }
}
