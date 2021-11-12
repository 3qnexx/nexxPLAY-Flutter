package tv.nexx.flutter.android.android.event;

import android.content.res.Configuration;

import java.util.Objects;

public class OnPIPModeChangedEvent implements AndroidEvent {

    private final boolean isInPictureInPictureMode;
    private final Configuration newConfig;

    private OnPIPModeChangedEvent(boolean isInPictureInPictureMode, Configuration newConfig) {
        this.isInPictureInPictureMode = isInPictureInPictureMode;
        this.newConfig = newConfig;
    }

    public static OnPIPModeChangedEvent create(boolean isInPictureInPictureMode, Configuration newConfig) {
        return new OnPIPModeChangedEvent(isInPictureInPictureMode, newConfig);
    }

    @Override
    public void visit(AndroidEventVisitor visitor) {
        visitor.visit(this);
    }

    public boolean isInPictureInPictureMode() {
        return isInPictureInPictureMode;
    }

    public Configuration getNewConfig() {
        return newConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnPIPModeChangedEvent that = (OnPIPModeChangedEvent) o;
        if (isInPictureInPictureMode != that.isInPictureInPictureMode) return false;
        return Objects.equals(newConfig, that.newConfig);
    }

    @Override
    public int hashCode() {
        int result = (isInPictureInPictureMode ? 1 : 0);
        result = 31 * result + (newConfig != null ? newConfig.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OnPIPModeChangedEvent{" +
                "isInPictureInPictureMode=" + isInPictureInPictureMode +
                ", newConfig=" + newConfig +
                '}';
    }
}
