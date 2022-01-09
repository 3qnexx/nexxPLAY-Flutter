package tv.nexx.flutter.android.platform_view;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tv.nexx.android.play.NexxPLAYEnvironment;

public class NexxPlayInitializationArguments {
    private final Map<String, Object> environmentProperties;
    private final boolean enableChromeCast;

    public NexxPlayInitializationArguments(Map<String, Object> environmentProperties, boolean enableChromeCast) {
        this.environmentProperties = environmentProperties;
        this.enableChromeCast = enableChromeCast;
    }

    public NexxPLAYEnvironment environment() {
        return new NexxPLAYEnvironment(environmentProperties);
    }

    public NexxPLAYEnvironment environment(Map<String, Object> additional) {
        final Map<String, Object> extendedProperties = new HashMap<>(environmentProperties);
        for (Map.Entry<String, Object> entry : additional.entrySet()) {
            extendedProperties.put(entry.getKey(), entry.getValue());
        }
        return new NexxPLAYEnvironment(extendedProperties);
    }

    public boolean shouldEnableChromeCast() {
        return enableChromeCast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NexxPlayInitializationArguments that = (NexxPlayInitializationArguments) o;
        return enableChromeCast == that.enableChromeCast && Objects.equals(environmentProperties, that.environmentProperties);
    }

    @Override
    public int hashCode() {
        int result = environmentProperties != null ? environmentProperties.hashCode() : 0;
        result = 31 * result + (enableChromeCast ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NexxPlayInitializationArguments{" +
                "environmentProperties=" + environmentProperties +
                ", enableChromeCast=" + enableChromeCast +
                '}';
    }
}
