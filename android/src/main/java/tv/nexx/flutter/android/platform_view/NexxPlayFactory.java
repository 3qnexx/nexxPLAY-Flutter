package tv.nexx.flutter.android.platform_view;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.Lifecycle;

import java.util.Objects;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import tv.nexx.android.play.INexxPLAY;
import tv.nexx.android.play.NexxPLAY;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.estd.functional.Supplier;
import tv.nexx.flutter.android.estd.observer.Subject;
import tv.nexx.flutter.android.nexxplay.NexxPlayConfiguration;

public final class NexxPlayFactory extends PlatformViewFactory {

    private final BinaryMessenger messenger;
    private final NexxPlayConfigurationFactory factory;
    private final Supplier<Lifecycle> lifecycle;
    private final Subject<AndroidEvent> subject;
    private final String pluginId;

    private NexxPlayFactory(BinaryMessenger messenger,
                            NexxPlayConfigurationFactory factory,
                            Supplier<Lifecycle> lifecycle,
                            Subject<AndroidEvent> subject,
                            String pluginId) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = Objects.requireNonNull(messenger);
        this.factory = Objects.requireNonNull(factory);
        this.lifecycle = Objects.requireNonNull(lifecycle);
        this.subject = Objects.requireNonNull(subject);
        this.pluginId = pluginId;
    }

    public static NexxPlayFactory from(BinaryMessenger messenger,
                                       NexxPlayConfigurationFactory factory,
                                       Supplier<Lifecycle> lifecycleFactory,
                                       Subject<AndroidEvent> subject,
                                       String pluginId) {
        return new NexxPlayFactory(messenger, factory, lifecycleFactory, subject, pluginId);
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        if (!(context instanceof Activity)) throw new NonActivityNexxPlayHostException();
        final NexxPlayConfiguration configuration = Objects.requireNonNull(factory.fromFlutterArguments(args));
        return createPlayer(messenger, lifecycle, subject, (Activity) context,
                NexxPlayInstanceID.create(viewId, pluginId), configuration);
    }

    private NexxPlayPlatformView createPlayer(BinaryMessenger messenger,
                                              Supplier<Lifecycle> lifecycle,
                                              Subject<AndroidEvent> subject,
                                              Activity activity,
                                              NexxPlayInstanceID id,
                                              NexxPlayConfiguration configuration) {
        final NexxPlayViewHost host = NexxPlayViewHost.create(activity);
        final INexxPLAY player = new NexxPLAY(activity, host.getPlayerArea(), activity.getWindow());
        final MethodChannel methodChannel = new MethodChannel(messenger, id.methodChannel());
        final EventChannel eventChannel = new EventChannel(messenger, id.eventChannel());
        final NexxPlayPlatformViewState state = new NexxPlayPlatformViewState(lifecycle, subject, id,
                configuration, methodChannel, eventChannel, host.getRoot(), player);
        final NexxPlayPlatformView view = new NexxPlayPlatformView(state);
        view.initialize();
        return view;
    }

}
