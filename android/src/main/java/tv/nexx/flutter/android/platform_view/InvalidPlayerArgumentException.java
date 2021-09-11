package tv.nexx.flutter.android.platform_view;

public class InvalidPlayerArgumentException extends IllegalArgumentException {
    public InvalidPlayerArgumentException(Object args) {
        super("Invalid player arguments: " + args);
    }
}
