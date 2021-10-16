package tv.nexx.flutter.android.platform_view;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.Lifecycle;

import java.util.Objects;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.estd.functional.Supplier;
import tv.nexx.flutter.android.estd.observer.Subject;
import tv.nexx.flutter.android.nexx_player.NexxPlayerConfiguration;

public final class NexxPlayerFactory extends PlatformViewFactory {

    private final BinaryMessenger messenger;
    private final NexxPlayerConfigurationFactory factory;
    private final Supplier<Lifecycle> lifecycle;
    private final Subject<AndroidEvent> subject;

    private NexxPlayerFactory(BinaryMessenger messenger,
                              NexxPlayerConfigurationFactory factory,
                              Supplier<Lifecycle> lifecycle,
                              Subject<AndroidEvent> subject) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = Objects.requireNonNull(messenger);
        this.factory = Objects.requireNonNull(factory);
        this.lifecycle = Objects.requireNonNull(lifecycle);
        this.subject = Objects.requireNonNull(subject);
    }

    public static NexxPlayerFactory from(BinaryMessenger messenger,
                                         NexxPlayerConfigurationFactory factory,
                                         Supplier<Lifecycle> lifecycleFactory,
                                         Subject<AndroidEvent> subject) {
        return new NexxPlayerFactory(messenger, factory, lifecycleFactory, subject);
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        if (!(context instanceof Activity)) throw new NonActivityNexxPlayerHostException();
        final NexxPlayerConfiguration configuration = Objects.requireNonNull(factory.fromFlutterArguments(args));
        return NexxPlayerPlatformView.create(messenger, lifecycle, subject, (Activity) context,
                viewId, configuration);
    }

}
