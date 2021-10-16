package tv.nexx.flutter.android.android.event;

public interface AndroidEventVisitor {
    void visit(OnUserLeaveHintEvent event);

    void visit(OnPIPModeChangedEvent event);
}
