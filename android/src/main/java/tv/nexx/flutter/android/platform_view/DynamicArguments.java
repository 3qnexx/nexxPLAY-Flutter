package tv.nexx.flutter.android.platform_view;

import androidx.annotation.NonNull;

import java.util.Map;
import java.util.Objects;

public class DynamicArguments {

    private final Map<Object, Object> arguments;

    private DynamicArguments(Map<Object, Object> arguments) {
        this.arguments = Objects.requireNonNull(arguments);
    }

    public static DynamicArguments from(Map<Object, Object> arguments) {
        return new DynamicArguments(arguments);
    }

    public String getString(Object key) {
        return derive(key, String.class);
    }
    
    @SuppressWarnings("rawtypes")
    public Map getMap(Object key) {
        return derive(key, Map.class);
    }

    private <T> T derive(Object key, Class<T> expected) {
        final Object value = arguments.get(key);
        if (value == null) {
            return null;
        } else if (!expected.isInstance(value)) {
            throw new IllegalStateException(deriveInvalidTypeMessage(key, expected, value));
        } else {
            return expected.cast(value);
        }
    }

    @NonNull
    private <T> String deriveInvalidTypeMessage(Object key, Class<T> expected, Object value) {
        return "Value for key [" + key + "] is not of the expected type " +
                "[" + expected.getSimpleName() + "]: " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicArguments that = (DynamicArguments) o;
        return Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return arguments.hashCode();
    }

    @Override
    public String toString() {
        return "DynamicArguments{" +
                "arguments=" + arguments +
                '}';
    }
}
