package tv.nexx.flutter.android.platform_view;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NexxPlayInitializationArgumentsFactory {
    // Can be used to shrink any Flutter-specific keys before they will be passed to the native
    // NexxEnvironment.
    private static final List<String> BRIDGING_KEYS = Collections.emptyList();

    private NexxPlayInitializationArgumentsFactory() {
    }

    public static NexxPlayInitializationArgumentsFactory create() {
        return new NexxPlayInitializationArgumentsFactory();
    }

    @SuppressWarnings("unchecked")
    public NexxPlayInitializationArguments fromFlutterArguments(Object args) {
        if (!(args instanceof Map)) {
            throw new InvalidPlayerArgumentsException(args);
        }
        final DynamicArguments arguments = DynamicArguments.from((Map<Object, Object>) args);
        final Map<String, Object> environment = (Map<String, Object>) arguments.getMap("environment");
        final Map<String, Object> envMap = removeBridgingKeys(environment);
        return new NexxPlayInitializationArguments(envMap);
    }

    private Map<String, Object> removeBridgingKeys(Map<String, Object> target) {
        final Map<String, Object> result = new HashMap<>(target);
        for (final String key : BRIDGING_KEYS) result.remove(key);
        return result;
    }
}
