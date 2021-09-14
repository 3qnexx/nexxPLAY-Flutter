package tv.nexx.flutter.android;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.FlutterLifecycleAdapter;
import io.flutter.plugin.common.BinaryMessenger;
import tv.nexx.flutter.android.platform_view.NexxPlayerConfigurationFactory;
import tv.nexx.flutter.android.platform_view.NexxPlayerFactory;

public final class AndroidPlugin implements FlutterPlugin, ActivityAware {

    private final LifecycleReference lifecycleReference = LifecycleReference.empty();

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        final BinaryMessenger messenger = flutterPluginBinding.getBinaryMessenger();
        final NexxPlayerFactory factory =
                NexxPlayerFactory.from(messenger, NexxPlayerConfigurationFactory.create(), lifecycleReference);
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
