package tv.nexx.flutter.android.platform_view;

import android.annotation.SuppressLint;

public class NexxPlayInstanceID {
    private final int instanceId;
    private final String pluginId;

    private NexxPlayInstanceID(int instanceId, String pluginId) {
        this.instanceId = instanceId;
        this.pluginId = pluginId;
    }

    public static NexxPlayInstanceID create(int instanceId, String pluginId) {
        return new NexxPlayInstanceID(instanceId, pluginId);
    }

    public int numeric() {
        return instanceId;
    }

    @SuppressLint("DefaultLocale")
    public String methodChannel() {
        return String.format("%s_%d_methods", pluginId, instanceId);
    }

    @SuppressLint("DefaultLocale")
    public String eventChannel() {
        return String.format("%s_%d_events", pluginId, instanceId);
    }
}
