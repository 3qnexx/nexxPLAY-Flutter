package tv.nexx.flutter.android.platform_view;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class NexxPlayMethodResult {

    private final Map<String, Object> payload;

    private NexxPlayMethodResult(Map<String, Object> payload) {
        this.payload = payload;
    }

    public static NexxPlayMethodResult from(int id) {
        return new NexxPlayMethodResult(identifiedPayload(id));
    }

    @NonNull
    private static Map<String, Object> identifiedPayload(int id) {
        final Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        return result;
    }

    public NexxPlayMethodResult put(String key, Object value) {
        payload.put(key, value);
        return this;
    }

    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(payload);
    }
}
