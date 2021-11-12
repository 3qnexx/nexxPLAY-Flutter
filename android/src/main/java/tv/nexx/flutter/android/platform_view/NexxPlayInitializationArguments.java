package tv.nexx.flutter.android.platform_view;

import java.util.Objects;

import tv.nexx.android.play.NexxPLAYEnvironment;

public class NexxPlayInitializationArguments {
    private final NexxPLAYEnvironment environment;

    public NexxPlayInitializationArguments(NexxPLAYEnvironment environment) {
        this.environment = environment;
    }

    public NexxPLAYEnvironment nexxPLAYEnvironment() {
        return environment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NexxPlayInitializationArguments that = (NexxPlayInitializationArguments) o;
        return Objects.equals(environment, that.environment);
    }

    @Override
    public int hashCode() {
        return environment != null ? environment.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NexxPlayInitializationArguments{" +
                "environment=" + environment +
                '}';
    }
}
