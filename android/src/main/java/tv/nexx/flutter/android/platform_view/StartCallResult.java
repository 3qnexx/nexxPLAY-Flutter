package tv.nexx.flutter.android.platform_view;

import java.util.Map;
import java.util.Objects;

class StartCallResult implements CallResult {
    private final NexxPlayerInstanceID id;
    private final boolean isStarted;

    @SuppressWarnings("SameParameterValue")
    private StartCallResult(NexxPlayerInstanceID id, boolean isStarted) {
        this.id = id;
        this.isStarted = isStarted;
    }

    static StartCallResult started(NexxPlayerInstanceID id) {
        return new StartCallResult(id, true);
    }

    @Override
    public Map<String, Object> asMap() {
        return NexxPlayerMethodResult.from(id.numeric()).put("started", isStarted).asMap();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartCallResult that = (StartCallResult) o;
        if (isStarted != that.isStarted) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isStarted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StartCallResult{" +
                "id=" + id +
                ", isStarted=" + isStarted +
                '}';
    }
}
