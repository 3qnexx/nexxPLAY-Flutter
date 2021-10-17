package tv.nexx.flutter.android.android.event;

public interface AndroidEventVisitor {
    @SuppressWarnings("unused")
    void visit(OnUserLeaveHintEvent event);

    void visit(OnPIPModeChangedEvent event);
}
