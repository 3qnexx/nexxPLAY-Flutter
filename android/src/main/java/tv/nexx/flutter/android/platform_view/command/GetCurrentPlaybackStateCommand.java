package tv.nexx.flutter.android.platform_view.command;

import java.util.HashMap;
import java.util.Map;

import tv.nexx.android.play.CurrentPlaybackState;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class GetCurrentPlaybackStateCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private GetCurrentPlaybackStateCommand() {
    }

    public static GetCurrentPlaybackStateCommand create() {
        return new GetCurrentPlaybackStateCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final CurrentPlaybackState playbackState = receiver.state().player().getCurrentPlaybackState();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id)
                .put("playback_state", serialize(playbackState));
        payload.result().success(result.asMap());
    }

    private Map<String, Object> serialize(CurrentPlaybackState playbackState) {
        final Map<String, Object> result = new HashMap<>();
        result.put("media_session", playbackState.getMediaSession());
        result.put("audio_language", playbackState.getAudioLanguage());
        result.put("play_reason", playbackState.getPlayReason());
        result.put("ab_test_version", playbackState.getAbTestVersion());
        result.put("current_time", playbackState.getCurrentTime());
        result.put("elapsed_time", playbackState.getElapsedTime());
        result.put("duration", playbackState.getDuration());
        result.put("is_auto_play", playbackState.getIsAutoPlay());
        result.put("is_muted", playbackState.getIsMuted());
        result.put("is_playing_ad", playbackState.getIsPlayingAd());
        result.put("is_local_media", playbackState.getIsLocalMedia());
        result.put("is_in_pip", playbackState.getIsInPiP());
        result.put("is_in_fullscreen", playbackState.getIsInFullscreen());
        result.put("is_casting", playbackState.getIsCasting());
        result.put("can_be_commented", playbackState.getCanBeCommented());
        result.put("is_stitched", playbackState.getIsStitched());
        result.put("is_in_pop_out", playbackState.getIsInPopOut());
        result.put("text_track_language", playbackState.getTextTrackLanguage());
        result.put("playback_mode", playbackState.getPlaybackMode());
        result.put("protection", playbackState.getProtection());
        result.put("steaming_filter", playbackState.getStreamingFilter());
        result.put("protocol", playbackState.getProtocol());
        result.put("codec", playbackState.getCodec());
        result.put("playback_state", playbackState.getPlaybackState());
        result.put("is_playing", playbackState.getIsPlaying());
        result.put("is_hdr", playbackState.getIsHDR());
        result.put("is_waiting_for_premiere", playbackState.getIsWaitingForPremiere());
        result.put("is_re_live", playbackState.getIsReLive());
        return result;
    }
}
