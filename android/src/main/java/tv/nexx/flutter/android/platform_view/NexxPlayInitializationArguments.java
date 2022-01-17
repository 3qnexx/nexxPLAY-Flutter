package tv.nexx.flutter.android.platform_view;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tv.nexx.android.play.NexxPLAYEnvironment;

public class NexxPlayInitializationArguments {
    private final Map<String, Object> environmentProperties;

    public NexxPlayInitializationArguments(Map<String, Object> environmentProperties) {
        this.environmentProperties = environmentProperties;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NexxPlayInitializationArguments that = (NexxPlayInitializationArguments) o;
        return Objects.equals(environmentProperties, that.environmentProperties);
    }

    @Override
    public int hashCode() {
        return environmentProperties != null ? environmentProperties.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NexxPlayInitializationArguments{" +
                "environmentProperties=" + environmentProperties +
                '}';
    }
}
