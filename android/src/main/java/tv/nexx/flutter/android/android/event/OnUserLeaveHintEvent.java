package tv.nexx.flutter.android.android.event;

public class OnUserLeaveHintEvent implements AndroidEvent {

    private static final OnUserLeaveHintEvent INSTANCE = new OnUserLeaveHintEvent();

    private OnUserLeaveHintEvent() {
    }

    public static OnUserLeaveHintEvent create() {
        return INSTANCE;
    }

    @Override
    public void visit(AndroidEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "OnUserLeaveHintEvent{}";
    }
}
