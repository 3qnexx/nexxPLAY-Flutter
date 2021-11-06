package tv.nexx.flutter.android.platform_view;

import java.util.Objects;

import tv.nexx.android.play.NexxPLAYConfiguration;
import tv.nexx.android.play.NexxPLAYEnvironment;

public class NexxPlayInitializationArguments {
    private final NexxPLAYEnvironment environment;
    private final NexxPLAYConfiguration configuration;

    public NexxPlayInitializationArguments(NexxPLAYEnvironment environment,
                                           NexxPLAYConfiguration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }

    public NexxPLAYEnvironment nexxPLAYEnvironment() {
        return environment;
    }

    public NexxPLAYConfiguration nexxPLAYConfiguration() {
        return configuration;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NexxPlayInitializationArguments that = (NexxPlayInitializationArguments) o;
        return Objects.equals(environment, that.environment) && Objects.equals(configuration, that.configuration);
    }

    @Override
    public int hashCode() {
        int result = environment != null ? environment.hashCode() : 0;
        result = 31 * result + (configuration != null ? configuration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NexxPlayInitializationArguments{" +
                "environment=" + environment +
                ", configuration=" + configuration +
                '}';
    }
}
