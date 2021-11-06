package tv.nexx.flutter.android.platform_view;

import java.util.Objects;

import tv.nexx.android.play.NexxPLAYConfiguration;

class NexxPlayPlaybackPayload {
    private final NexxPLAYConfiguration configuration;
    private final NexxPlayPlaybackConfiguration playback;

    private NexxPlayPlaybackPayload(NexxPLAYConfiguration configuration,
                                    NexxPlayPlaybackConfiguration playback) {
        this.configuration = configuration;
        this.playback = playback;
    }

    static NexxPlayPlaybackPayload of(NexxPLAYConfiguration configuration,
                                      NexxPlayPlaybackConfiguration playback) {
        return new NexxPlayPlaybackPayload(configuration, playback);
    }

    public NexxPLAYConfiguration configuration() {
        return configuration;
    }

    public NexxPlayPlaybackConfiguration playback() {
        return playback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NexxPlayPlaybackPayload that = (NexxPlayPlaybackPayload) o;
        return Objects.equals(configuration, that.configuration) &&
                Objects.equals(playback, that.playback);
    }

    @Override
    public int hashCode() {
        int result = configuration != null ? configuration.hashCode() : 0;
        result = 31 * result + (playback != null ? playback.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NexxPlayPlaybackPayload{" +
                "configuration=" + configuration +
                ", playback=" + playback +
                '}';
    }
}
