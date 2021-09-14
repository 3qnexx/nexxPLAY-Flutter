package tv.nexx.flutter.android.nexx_player;

import static tv.nexx.android.play.NexxPLAYEnvironment.alwaysInFullscreen;
import static tv.nexx.android.play.NexxPLAYEnvironment.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tv.nexx.android.play.MediaSourceType;
import tv.nexx.android.play.NexxPLAYConfiguration;
import tv.nexx.android.play.NexxPLAYEnvironment;

public class NexxPlayerConfiguration {
    private final String provider;
    private final String domainID;
    private final String mediaID;
    private final String playMode;
    private final Boolean autoplay;
    private final Boolean autoNext;
    private final Boolean disableAds;
    private final String dataMode;
    private final Boolean hidePrevNext;
    private final Boolean forcePrevNext;
    private final String exitMode;
    private final Integer startPosition;
    private final Float delay;
    private final Boolean startFullscreen;
    private final String mediaSourceType;
    private final String streamingFilter;
    private final String adType;

    public NexxPlayerConfiguration(String provider,
                                   String domainID,
                                   String mediaID,
                                   String playMode,
                                   Boolean autoplay,
                                   Boolean autoNext,
                                   Boolean disableAds,
                                   String dataMode,
                                   Boolean hidePrevNext,
                                   Boolean forcePrevNext,
                                   String exitMode,
                                   Integer startPosition,
                                   Float delay,
                                   Boolean startFullscreen,
                                   String mediaSourceType,
                                   String streamingFilter,
                                   String adType) {
        this.provider = provider;
        this.domainID = domainID;
        this.mediaID = mediaID;
        this.playMode = playMode;
        this.autoplay = autoplay;
        this.autoNext = autoNext;
        this.disableAds = disableAds;
        this.dataMode = dataMode;
        this.hidePrevNext = hidePrevNext;
        this.forcePrevNext = forcePrevNext;
        this.exitMode = exitMode;
        this.startPosition = startPosition;
        this.delay = delay;
        this.startFullscreen = startFullscreen;
        this.mediaSourceType = mediaSourceType;
        this.streamingFilter = streamingFilter;
        this.adType = adType;
    }

    public NexxPLAYEnvironment nexxPLAYEnvironment() {
        final Map<String, Object> result = new HashMap<>();
        result.put(domain, domainID);
        result.put(alwaysInFullscreen, startFullscreen);
        return new NexxPLAYEnvironment(result);
    }

    public NexxPLAYConfiguration nexxPLAYConfiguration() {
        final HashMap<String, Object> result = new HashMap<>();
        result.put(NexxPLAYConfiguration.dataMode, dataMode);
        result.put(NexxPLAYConfiguration.hidePrevNext, hidePrevNext ? 1 : 0);
        result.put(NexxPLAYConfiguration.forcePrevNext, forcePrevNext ? 1 : 0);
        result.put(NexxPLAYConfiguration.autoPlay, autoplay ? 1 : 0);
        result.put(NexxPLAYConfiguration.autoNext, autoNext ? 1 : 0);
        result.put(NexxPLAYConfiguration.exitMode, exitMode);
        result.put(NexxPLAYConfiguration.streamingFilter, streamingFilter);
        result.put(NexxPLAYConfiguration.delay, delay);
        result.put(NexxPLAYConfiguration.startPosition, startPosition);
        result.put(NexxPLAYConfiguration.adType, adType);
        result.put(NexxPLAYConfiguration.disableAds, disableAds ? 1 : 0);
        return new NexxPLAYConfiguration(result);
    }

    public MediaSourceType getMediaSourceType() {
        for (MediaSourceType value : MediaSourceType.values()) {
            if (mediaSourceType.equals(value.toString())) return value;
        }
        throw new IllegalStateException("Undefined media source type: " + mediaSourceType);
    }

    public String getPlayMode() {
        return playMode;
    }

    public String getMediaID() {
        return mediaID;
    }

    public String getProvider() {
        return provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NexxPlayerConfiguration that = (NexxPlayerConfiguration) o;
        return Objects.equals(autoplay, that.autoplay) &&
                Objects.equals(autoNext, that.autoNext) &&
                Objects.equals(disableAds, that.disableAds) &&
                Objects.equals(hidePrevNext, that.hidePrevNext) &&
                Objects.equals(forcePrevNext, that.forcePrevNext) &&
                Objects.equals(startPosition, that.startPosition) &&
                (delay == null ? that.delay == null : that.delay != null && Float.compare(delay, that.delay) == 0) &&
                Objects.equals(startFullscreen, that.startFullscreen) &&
                Objects.equals(provider, that.provider) &&
                Objects.equals(domainID, that.domainID) &&
                Objects.equals(mediaID, that.mediaID) &&
                Objects.equals(playMode, that.playMode) &&
                Objects.equals(dataMode, that.dataMode) &&
                Objects.equals(exitMode, that.exitMode) &&
                Objects.equals(mediaSourceType, that.mediaSourceType) &&
                Objects.equals(streamingFilter, that.streamingFilter) &&
                Objects.equals(adType, that.adType);
    }

    @Override
    public int hashCode() {
        int result = provider != null ? provider.hashCode() : 0;
        result = 31 * result + (domainID != null ? domainID.hashCode() : 0);
        result = 31 * result + (mediaID != null ? mediaID.hashCode() : 0);
        result = 31 * result + (playMode != null ? playMode.hashCode() : 0);
        result = 31 * result + (autoplay ? 1 : 0);
        result = 31 * result + (autoNext ? 1 : 0);
        result = 31 * result + (disableAds ? 1 : 0);
        result = 31 * result + (dataMode != null ? dataMode.hashCode() : 0);
        result = 31 * result + (hidePrevNext ? 1 : 0);
        result = 31 * result + (forcePrevNext ? 1 : 0);
        result = 31 * result + (exitMode != null ? exitMode.hashCode() : 0);
        result = 31 * result + startPosition;
        result = 31 * result + (delay != +0.0f ? Float.floatToIntBits(delay) : 0);
        result = 31 * result + (startFullscreen ? 1 : 0);
        result = 31 * result + (mediaSourceType != null ? mediaSourceType.hashCode() : 0);
        result = 31 * result + (streamingFilter != null ? streamingFilter.hashCode() : 0);
        result = 31 * result + (adType != null ? adType.hashCode() : 0);
        return result;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "NexxPlayerConfiguration{" +
                "provider='" + provider + '\'' +
                ", domainID='" + domainID + '\'' +
                ", mediaID='" + mediaID + '\'' +
                ", playMode='" + playMode + '\'' +
                ", autoplay=" + autoplay +
                ", autoNext=" + autoNext +
                ", disableAds=" + disableAds +
                ", dataMode='" + dataMode + '\'' +
                ", hidePrevNext=" + hidePrevNext +
                ", forcePrevNext=" + forcePrevNext +
                ", exitMode='" + exitMode + '\'' +
                ", startPosition=" + startPosition +
                ", delay=" + delay +
                ", startFullscreen=" + startFullscreen +
                ", mediaSourceType='" + mediaSourceType + '\'' +
                ", streamingFilter='" + streamingFilter + '\'' +
                ", adType='" + adType + '\'' +
                '}';
    }
}
