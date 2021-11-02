package tv.nexx.flutter.android.platform_view;

import java.util.Map;

import tv.nexx.flutter.android.nexxplay.NexxPlayConfiguration;

public class NexxPlayConfigurationFactory {

    private NexxPlayConfigurationFactory() {}

    public static NexxPlayConfigurationFactory create() {
        return new NexxPlayConfigurationFactory();
    }

    @SuppressWarnings("unchecked")
    public NexxPlayConfiguration fromFlutterArguments(Object args) {
        if (!(args instanceof Map)) throw new InvalidPlayerArgumentsException(args);
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
        final Double delay = arguments.getDouble("delay");
        final Boolean startFullscreen = arguments.getBoolean("startFullscreen");
        final String mediaSourceType = arguments.getString("mediaSourceType");
        final String streamingFilter = arguments.getString("streamingFilter");
        final String adType = arguments.getString("adType");
        final Float delayF = delay == null ? null : delay.floatValue();
        return new NexxPlayConfiguration(provider, domainID, mediaID, playMode, autoplay,
                autoNext, disableAds, dataMode, hidePrevNext, forcePrevNext, exitMode,
                startPosition, delayF, startFullscreen, mediaSourceType, streamingFilter, adType);
    }
}
