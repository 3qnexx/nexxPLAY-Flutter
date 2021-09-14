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

    public Boolean getBoolean(Object key) {
        return derive(key, Boolean.class);
    }

    public Integer getInteger(Object key) {
        return derive(key, Integer.class);
    }

    public Double getDouble(Object key) {
        return derive(key, Double.class);
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

}
