package tv.nexx.flutter.android.platform_view.command;

import java.util.Map;
import java.util.Objects;

import tv.nexx.android.play.MediaSourceType;
import tv.nexx.flutter.android.platform_view.DynamicArguments;

class NexxPlayPlaybackConfiguration {
    private final MediaSourceType mediaSourceType;
    private final String mediaID;
    private final String playMode;
    private final String provider;

    private NexxPlayPlaybackConfiguration(MediaSourceType mediaSourceType,
                                          String mediaID,
                                          String playMode,
                                          String provider) {
        this.mediaSourceType = mediaSourceType;
        this.mediaID = mediaID;
        this.playMode = playMode;
        this.provider = provider;
    }

    static NexxPlayPlaybackConfiguration from(Map<Object, Object> map) {
        final DynamicArguments arguments = DynamicArguments.from(map);
        return new NexxPlayPlaybackConfiguration(
                deductMediaSourceType(arguments.getString("mediaSourceType")),
                arguments.getString("mediaID"),
                arguments.getString("playMode"),
                arguments.getString("provider")
        );
    }

    private static MediaSourceType deductMediaSourceType(String raw) {
        for (MediaSourceType value : MediaSourceType.values()) {
            if (raw.equals(value.toString())) return value;
        }
        throw new IllegalStateException("Undefined media source type: " + raw);
    }

    public MediaSourceType mediaSourceType() {
        return mediaSourceType;
    }

    public String mediaID() {
        return mediaID;
    }

    public String playMode() {
        return playMode;
    }

    public String provider() {
        return provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NexxPlayPlaybackConfiguration that = (NexxPlayPlaybackConfiguration) o;
        return Objects.equals(mediaSourceType, that.mediaSourceType) &&
                Objects.equals(mediaID, that.mediaID) &&
                Objects.equals(playMode, that.playMode) &&
                Objects.equals(provider, that.provider);
    }

    @Override
    public int hashCode() {
        int result = mediaSourceType != null ? mediaSourceType.hashCode() : 0;
        result = 31 * result + (mediaID != null ? mediaID.hashCode() : 0);
        result = 31 * result + (playMode != null ? playMode.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NexxPlayPlaybackConfiguration{" +
                "mediaSourceType='" + mediaSourceType + '\'' +
                ", mediaID='" + mediaID + '\'' +
                ", playMode='" + playMode + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }
}
