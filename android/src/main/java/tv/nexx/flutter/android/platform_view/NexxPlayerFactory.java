package tv.nexx.flutter.android.platform_view;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.Lifecycle;

import java.util.Map;
import java.util.Objects;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import tv.nexx.flutter.android.estd.functional.Supplier;
import tv.nexx.flutter.android.nexx_player.NexxPlayerConfiguration;

public final class NexxPlayerFactory extends PlatformViewFactory {

    private final BinaryMessenger messenger;
    private final Supplier<Lifecycle> lifecycle;

    private NexxPlayerFactory(BinaryMessenger messenger, Supplier<Lifecycle> lifecycle) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = Objects.requireNonNull(messenger);
        this.lifecycle = Objects.requireNonNull(lifecycle);
    }

    public static NexxPlayerFactory from(BinaryMessenger messenger, Supplier<Lifecycle> lifecycleFactory) {
        return new NexxPlayerFactory(messenger, lifecycleFactory);
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        if (!(context instanceof Activity)) throw new NonActivityNexxPlayerHostException();
        final NexxPlayerConfiguration configuration = Objects.requireNonNull(deriveConfiguration(args));
        return NexxPlayerPlatformView.create(viewId, configuration, (Activity) context, lifecycle, messenger);
    }

    @SuppressWarnings("unchecked")
    private NexxPlayerConfiguration deriveConfiguration(Object args) {
        if (!(args instanceof Map)) throw new InvalidPlayerArgumentException(args);
        final DynamicArguments arguments = DynamicArguments.from((Map<Object, Object>) args);
        final String provider = arguments.getString("provider");
        final String domainID = arguments.getString("domainID");
        final String mediaID = arguments.getString("mediaID");
        final String playMode = arguments.getString("playMode");
        final Boolean autoplay = arguments.getBoolean("autoplay");
        final Boolean autoNext = arguments.getBoolean("autoNext");
        final Boolean disableAds = arguments.getBoolean("disableAds");
        final String dataMode = arguments.getString("dataMode");
        final Boolean hidePrevNext = arguments.getBoolean("hidePrevNext");
        final Boolean forcePrevNext = arguments.getBoolean("forcePrevNext");
        final String exitMode = arguments.getString("exitMode");
        final Integer startPosition = arguments.getInteger("startPosition");
        final Float delay = arguments.getFloat("delay");
        final Boolean startFullscreen = arguments.getBoolean("startFullscreen");
        final String mediaSourceType = arguments.getString("mediaSourceType");
        final String streamingFilter = arguments.getString("streamingFilter");
        final String adType = arguments.getString("adType");
        return new NexxPlayerConfiguration(provider, domainID, mediaID, playMode, autoplay,
                autoNext, disableAds, dataMode, hidePrevNext, forcePrevNext, exitMode,
                startPosition, delay, startFullscreen, mediaSourceType, streamingFilter, adType);
    }
}
