package tv.nexx.flutter.android;

import android.content.Context;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.FlutterLifecycleAdapter;
import tv.nexx.android.play.NexxPLAYConfiguration;
import tv.nexx.android.play.NexxPLAYEnvironment;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.android.lifecycle.LifecycleReference;
import tv.nexx.flutter.android.configuration.NexxPlayPluginConfiguration;
import tv.nexx.flutter.android.estd.functional.Function;
import tv.nexx.flutter.android.estd.observer.Channel;
import tv.nexx.flutter.android.estd.observer.MutableSubject;
import tv.nexx.flutter.android.platform_view.NexxPlayInitializationArgumentsFactory;
import tv.nexx.flutter.android.platform_view.NexxPlayFactory;

public final class NexxPlayPlugin implements FlutterPlugin, ActivityAware {

    public static final String KEY_MEDIA_SESSION = NexxPLAYEnvironment.mediaSession;
    public static final String KEY_AD_MANAGER = NexxPLAYEnvironment.adManager;
    public static final String KEY_CAST_CONTEXT = NexxPLAYEnvironment.castContext;
    public static final String KEY_NOTIFICATION_ICON = NexxPLAYConfiguration.notificationIcon;
    private static final String PLUGIN_IDENTIFIER = "tv.nexx.flutter.android";
    private static final MutableSubject<AndroidEvent> EVENT_SUBJECT = Channel.threadConfined();
    private static final NexxPlayPluginConfiguration ENVIRONMENT_CONFIGURATION = NexxPlayPluginConfiguration.create();
    private static final NexxPlayPluginConfiguration CONFIGURATION = NexxPlayPluginConfiguration.create();

    public static void addEnvironmentConfigurationEntry(String name, Object object) {
        ENVIRONMENT_CONFIGURATION.add(unused -> new NexxPlayPluginConfiguration.Entry(name, object));
    }

    public static void addEnvironmentConfigurationEntry(String name, Function<Context, Object> factory) {
        ENVIRONMENT_CONFIGURATION.add(context -> new NexxPlayPluginConfiguration.Entry(name, factory.apply(context)));
    }

    public static void addNativeConfigurationEntry(String name, Object object) {
        CONFIGURATION.add(unused -> new NexxPlayPluginConfiguration.Entry(name, object));
    }

    public static void addNativeConfigurationEntry(String name, Function<Context, Object> factory) {
        CONFIGURATION.add(context -> new NexxPlayPluginConfiguration.Entry(name, factory.apply(context)));
    }

    public static void post(AndroidEvent event) {
        EVENT_SUBJECT.publish(event);
    }

    private final LifecycleReference lifecycleReference = LifecycleReference.empty();

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        final NexxPlayFactory factory = NexxPlayFactory.from(
                ENVIRONMENT_CONFIGURATION,
                CONFIGURATION,
                NexxPlayInitializationArgumentsFactory.create(),
                flutterPluginBinding.getBinaryMessenger(),
                lifecycleReference,
                EVENT_SUBJECT,
                PLUGIN_IDENTIFIER
        );
        flutterPluginBinding.getPlatformViewRegistry()
                .registerViewFactory(PLUGIN_IDENTIFIER, factory);
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
