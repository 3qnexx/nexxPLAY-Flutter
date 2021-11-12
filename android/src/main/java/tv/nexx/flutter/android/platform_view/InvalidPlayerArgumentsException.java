package tv.nexx.flutter.android.platform_view;

public class InvalidPlayerArgumentsException extends IllegalArgumentException {
    public InvalidPlayerArgumentsException(Object args) {
        super("Invalid player arguments: " + args);
    }
}
