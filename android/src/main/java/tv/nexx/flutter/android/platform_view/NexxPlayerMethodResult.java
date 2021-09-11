package tv.nexx.flutter.android.platform_view;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class NexxPlayerMethodResult {

    private final Map<String, Object> payload;

    private NexxPlayerMethodResult(Map<String, Object> payload) {
        this.payload = payload;
    }

    public static NexxPlayerMethodResult from(int id) {
        return new NexxPlayerMethodResult(identifiedPayload(id));
    }

    public static NexxPlayerMethodResult from(int id, Map<String, Object> additional) {
        final Map<String, Object> payload = identifiedPayload(id);
        payload.putAll(additional);
        return new NexxPlayerMethodResult(payload);
    }

    @NonNull
    private static Map<String, Object> identifiedPayload(int id) {
        final Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        return result;
    }

    public NexxPlayerMethodResult put(String key, Object value) {
        payload.put(key, value);
        return this;
    }

    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(payload);
    }
}
