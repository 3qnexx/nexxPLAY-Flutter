package tv.nexx.flutter.android.platform_view;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import java.util.Map;
import java.util.Objects;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import tv.nexx.android.play.NexxPLAY;
import tv.nexx.android.play.NexxPLAYEnvironment;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.configuration.NexxPlayPluginEnvironmentConfiguration;
import tv.nexx.flutter.android.estd.functional.Supplier;
import tv.nexx.flutter.android.estd.observer.Subject;

public final class NexxPlayFactory extends PlatformViewFactory {

    private final BinaryMessenger messenger;
    private final NexxPlayInitializationArgumentsFactory factory;
    private final NexxPlayPluginEnvironmentConfiguration configuration;
    private final Supplier<Lifecycle> lifecycle;
    private final Subject<AndroidEvent> subject;
    private final String pluginId;

    private NexxPlayFactory(
            NexxPlayPluginEnvironmentConfiguration configuration,
            BinaryMessenger messenger,
            NexxPlayInitializationArgumentsFactory factory,
            Supplier<Lifecycle> lifecycle,
            Subject<AndroidEvent> subject,
            String pluginId) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = Objects.requireNonNull(messenger);
        this.factory = Objects.requireNonNull(factory);
        this.configuration = Objects.requireNonNull(configuration);
        this.lifecycle = Objects.requireNonNull(lifecycle);
        this.subject = Objects.requireNonNull(subject);
        this.pluginId = pluginId;
    }

    public static NexxPlayFactory from(
            NexxPlayPluginEnvironmentConfiguration configuration,
            NexxPlayInitializationArgumentsFactory factory,
            BinaryMessenger messenger,
            Supplier<Lifecycle> lifecycleFactory,
            Subject<AndroidEvent> subject,
            String pluginId) {
        return new NexxPlayFactory(configuration, messenger, factory, lifecycleFactory, subject, pluginId);
    }

    @NonNull
    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        if (!(context instanceof Activity)) {
            throw new NonActivityNexxPlayHostException();
        }
        final NexxPlayInitializationArguments arguments = Objects.requireNonNull(factory.fromFlutterArguments(args));
        return createPlayer(configuration, (Activity) context, NexxPlayInstanceID.create(viewId, pluginId), arguments);
    }

    private NexxPlayPlatformView createPlayer(
            NexxPlayPluginEnvironmentConfiguration configuration,
            Activity activity,
            NexxPlayInstanceID id,
            NexxPlayInitializationArguments arguments) {
        final NexxPlayViewHost host = NexxPlayViewHost.create(activity);
        final NexxPLAY player = new NexxPLAY(activity, host.getPlayerArea(), activity.getWindow());
        final MethodChannel methodChannel = new MethodChannel(messenger, id.methodChannel());
        final EventChannel eventChannel = new EventChannel(messenger, id.eventChannel());
        final Map<String, Object> contextAdditions = configuration.getPlayerEnvironment(activity.getApplicationContext());
        final NexxPLAYEnvironment env = arguments.environment(contextAdditions);
        final NexxPlayPlatformViewState state = new NexxPlayPlatformViewState(lifecycle, subject,
                id, env, methodChannel, eventChannel, host.getRoot(), player);
        final NexxPlayPlatformView view = new NexxPlayPlatformView(state);
        view.initialize();
        return view;
    }
}
