package tv.nexx.flutter.android.platform_view.command;

import java.util.HashMap;
import java.util.Map;

import tv.nexx.android.play.PlaybackState;
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
        final PlaybackState playbackState = receiver.state().player().getCurrentPlaybackState();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id)
                .put("playback_state", serialize(playbackState));
        payload.result().success(result.asMap());
    }

    private Map<String, Object> serialize(PlaybackState playbackState) {
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
        result.put("is_playing_bumper", playbackState.getIsPlayingBumper());
        result.put("is_local_media", playbackState.getIsLocalMedia());
        result.put("is_in_pip", playbackState.getIsInPiP());
        result.put("is_in_fullscreen", playbackState.getIsInFullscreen());
        result.put("is_casting", playbackState.getIsCasting());
        result.put("can_be_commented", playbackState.getCanBeCommented());
        result.put("live_status", playbackState.getLiveStatus());
        result.put("is_in_story_mode", playbackState.getIsInStoryMode());
        result.put("is_stitched", playbackState.getIsStitched());
        result.put("is_presentation_mode", playbackState.getIsPresentationMode());
        result.put("is_story_mode", playbackState.getIsStoryMode());
        result.put("is_scene_split_mode", playbackState.getIsSceneSplitMode());
        result.put("is_lights_out", playbackState.getIsLightsOut());
        result.put("is_in_pop_out", playbackState.getIsInPopOut());
        return result;
    }
}
