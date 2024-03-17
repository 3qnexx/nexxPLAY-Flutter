package tv.nexx.flutter.android.configuration;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import tv.nexx.flutter.android.estd.functional.Function;

public class NexxPlayPluginConfiguration {
    private final List<Function<Context, Entry>> factories;

    private NexxPlayPluginConfiguration(List<Function<Context, Entry>> factories) {
        this.factories = factories;
    }

    public static NexxPlayPluginConfiguration create() {
        return new NexxPlayPluginConfiguration(new CopyOnWriteArrayList<>());
    }

    public void add(Function<Context, Entry> factory) {
        factories.add(factory);
    }

    public Map<String, Object> intoMap(Context context) {
        final Map<String, Object> result = new HashMap<>();
        for (Function<Context, Entry> factory : factories) {
            final Entry entry = factory.apply(context);
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static class Entry {
        private final String key;
        private final Object value;

        public Entry(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
    }
}
