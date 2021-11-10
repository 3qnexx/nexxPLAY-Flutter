package tv.nexx.flutter.android.platform_view.command;

import java.util.HashMap;
import java.util.Map;

import tv.nexx.android.play.MediaData;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class GetMediaDataCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final GetMediaDataCommand INSTANCE = new GetMediaDataCommand();

    private GetMediaDataCommand() {
    }

    public static GetMediaDataCommand create() {
        return INSTANCE;
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final MediaData mediaData = receiver.state().player().getMediaData();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id)
                .put("media_data", serialize(mediaData));
        payload.result().success(result.asMap());
    }

    private Map<String, Object> serialize(MediaData mediaData) {
        final Map<String, Object> result = new HashMap<>();
        result.put("play_reason", mediaData.getPlayReason());
        result.put("is_playing_ad", mediaData.getIsPlayingAd());
        result.put("persons", mediaData.getPersons());
        result.put("domain", mediaData.getDomain());
        result.put("media_session_parent", mediaData.getMediaSessionParent());
        result.put("autoplay", mediaData.getAutoplay());
        result.put("studio", mediaData.getStudio());
        result.put("studio_ad_ref", mediaData.getStudio_adref());
        result.put("media_id", mediaData.getMediaId());
        result.put("hash", mediaData.getHash());
        result.put("title", mediaData.getTitle());
        result.put("subtitle", mediaData.getSubtitle());
        result.put("teaser", mediaData.getTeaser());
        result.put("description", mediaData.getDescription());
        result.put("channel", mediaData.getChannel());
        result.put("uploaded", mediaData.getUploaded());
        result.put("created", mediaData.getCreated());
        result.put("order_hint", mediaData.getOrderhint());
        result.put("is_presentation", mediaData.getIsPresentation());
        result.put("current_caption_language", mediaData.getCurrentCaptionLanguage());
        result.put("current_audio_language", mediaData.getCurrentAudioLanguage());
        result.put("channel_ad_ref", mediaData.getChannelAdref());
        result.put("channel_id", mediaData.getChannelID());
        result.put("thumb", mediaData.getThumb());
        result.put("thumb_ABT", mediaData.getThumbABT());
        result.put("stream_type", mediaData.getStreamtype());
        result.put("runtime", mediaData.getRuntime());
        result.put("license_by", mediaData.getLicenseBy());
        result.put("current_playback_speed", mediaData.getCurrentPlaybackSpeed());
        result.put("is_bumper", mediaData.getIsBumper());
        result.put("is_stitched", mediaData.getIsStitched());
        result.put("orientation", mediaData.getOrientation());
        result.put("has_audio", mediaData.getHasAudio());
        result.put("global_id", mediaData.getGlobalID());
        result.put("chosen_ab_version", mediaData.getChosenABVersion());
        result.put("playback_mode", mediaData.getPlaybackMode());
        result.put("is_remote_media", mediaData.getIsRemoteMedia());
        result.put("remote_reference", mediaData.getRemoteReference());
        result.put("is_story", mediaData.getIsStory());
        result.put("is_scene_split", mediaData.getIsSceneSplit());
        result.put("current_time", mediaData.getCurrentTime());
        result.put("current_duration", mediaData.getCurrentDuration());
        result.put("media_session", mediaData.getMediaSession());
        result.put("format_id", mediaData.getFormat_id());
        result.put("format", mediaData.getFormat());
        return result;
    }
}
