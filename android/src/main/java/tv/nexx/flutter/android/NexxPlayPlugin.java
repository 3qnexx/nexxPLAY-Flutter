package tv.nexx.flutter.android;

import androidx.annotation.NonNull;

import com.google.android.gms.cast.framework.CastContext;

import java.util.concurrent.Executors;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.FlutterLifecycleAdapter;
import tv.nexx.flutter.android.ads.MediaSessionReference;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.estd.functional.Either;
import tv.nexx.flutter.android.estd.observer.Channel;
import tv.nexx.flutter.android.estd.observer.MutableSubject;
import tv.nexx.flutter.android.platform_view.NexxPlayInitializationArgumentsFactory;
import tv.nexx.flutter.android.platform_view.NexxPlayFactory;

public final class NexxPlayPlugin implements FlutterPlugin, ActivityAware {

    private static final String PLUGIN_IDENTIFIER = "tv.nexx.flutter.android";
    private static final MutableSubject<AndroidEvent> EVENT_SUBJECT = Channel.threadConfined();

    public static void post(AndroidEvent event) {
        EVENT_SUBJECT.publish(event);
    }

    private final LifecycleReference lifecycleReference = LifecycleReference.empty();
    private final MediaSessionReference mediaSessionReference = MediaSessionReference.create();
    private final CastContextReference castContextReference = CastContextReference.empty();

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        final NexxPlayFactory factory = NexxPlayFactory.from(
                flutterPluginBinding.getBinaryMessenger(),
                mediaSessionReference,
                NexxPlayInitializationArgumentsFactory.create(),
                lifecycleReference,
                castContextReference,
                EVENT_SUBJECT,
                PLUGIN_IDENTIFIER
        );
        flutterPluginBinding.getPlatformViewRegistry()
                .registerViewFactory(PLUGIN_IDENTIFIER, factory);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        lifecycleReference.accept(FlutterLifecycleAdapter.getActivityLifecycle(binding));
        CastContext.getSharedInstance(binding.getActivity(), Executors.newSingleThreadExecutor())
                .addOnSuccessListener(castContext -> castContextReference.accept(Either.right(castContext)))
                .addOnFailureListener(throwable -> castContextReference.accept(Either.left(throwable)));
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
        castContextReference.accept(null);
        lifecycleReference.accept(null);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        // No-op by design
    }

}
