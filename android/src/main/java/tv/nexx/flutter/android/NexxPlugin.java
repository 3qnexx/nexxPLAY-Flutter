package tv.nexx.flutter.android;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.FlutterLifecycleAdapter;
import io.flutter.plugin.common.BinaryMessenger;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.estd.observer.Channel;
import tv.nexx.flutter.android.estd.observer.MutableSubject;
import tv.nexx.flutter.android.platform_view.NexxPlayerConfigurationFactory;
import tv.nexx.flutter.android.platform_view.NexxPlayerFactory;

public final class NexxPlugin implements FlutterPlugin, ActivityAware {

    private static final MutableSubject<AndroidEvent> EVENT_SUBJECT = Channel.threadConfined();

    public static void post(AndroidEvent event) {
        EVENT_SUBJECT.publish(event);
    }

    private final LifecycleReference lifecycleReference = LifecycleReference.empty();

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        final BinaryMessenger messenger = flutterPluginBinding.getBinaryMessenger();
        final NexxPlayerFactory factory = NexxPlayerFactory.from(messenger,
                NexxPlayerConfigurationFactory.create(),
                lifecycleReference,
                EVENT_SUBJECT);
        flutterPluginBinding.getPlatformViewRegistry()
                .registerViewFactory(NexxPluginEnvironment.PLUGIN_IDENTIFIER, factory);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        lifecycleReference.accept(FlutterLifecycleAdapter.getActivityLifecycle(binding));
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        onAttachedToActivity(binding);
    }

    @Override
    public void onDetachedFromActivity() {
        lifecycleReference.accept(null);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        // No-op by design
    }

}
