package tv.nexx.flutter.android.android.event;

public interface AndroidEvent {
    void visit(AndroidEventVisitor visitor);
}
