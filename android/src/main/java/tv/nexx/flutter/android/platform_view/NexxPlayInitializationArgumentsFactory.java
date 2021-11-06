package tv.nexx.flutter.android.platform_view;

import java.util.HashMap;
import java.util.Map;

import tv.nexx.android.play.NexxPLAYConfiguration;
import tv.nexx.android.play.NexxPLAYEnvironment;

public class NexxPlayInitializationArgumentsFactory {

    private NexxPlayInitializationArgumentsFactory() {
    }

    public static NexxPlayInitializationArgumentsFactory create() {
        return new NexxPlayInitializationArgumentsFactory();
    }

    @SuppressWarnings("unchecked")
    public NexxPlayInitializationArguments fromFlutterArguments(Object args) {
        if (!(args instanceof Map)) throw new InvalidPlayerArgumentsException(args);
        final DynamicArguments arguments = DynamicArguments.from((Map<Object, Object>) args);
        final Map<String, Object> environment = (Map<String, Object>) arguments.getMap("environment");
        final Map<String, Object> configuration = (Map<String, Object>) arguments.getMap("configuration");
        return new NexxPlayInitializationArguments(
                new NexxPLAYEnvironment(environment),
                new NexxPLAYConfiguration(new HashMap<>(configuration))
        );
    }
}
