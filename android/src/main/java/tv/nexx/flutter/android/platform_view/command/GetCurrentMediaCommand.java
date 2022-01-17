package tv.nexx.flutter.android.platform_view.command;

import java.util.HashMap;
import java.util.Map;

import tv.nexx.android.play.MediaData;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class GetCurrentMediaCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private GetCurrentMediaCommand() {
    }

    public static GetCurrentMediaCommand create() {
        return new GetCurrentMediaCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final MediaData mediaData = receiver.state().player().getCurrentMedia();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id)
                .put("media_data", serialize(mediaData));
        payload.result().success(result.asMap());
    }

    private Map<String, Object> serialize(MediaData mediaData) {
        final Map<String, Object> result = new HashMap<>();
        result.put("original_domain", mediaData.getOriginalDomain());
        result.put("studio", mediaData.getStudio());
        result.put("id", mediaData.getID());
        result.put("global_id", mediaData.getGlobalID());
        result.put("hash", mediaData.getHash());
        result.put("title", mediaData.getTitle());
        result.put("subtitle", mediaData.getSubtitle());
        result.put("channel", mediaData.getChannel());
        result.put("uploaded", mediaData.getUploaded());
        result.put("created", mediaData.getCreated());
        result.put("order_hint", mediaData.getOrderHint());
        result.put("persons", mediaData.getPersons());
        result.put("episode_of_series", mediaData.getEpisodeOfSeries());
        result.put("thumb", mediaData.getThumb());
        result.put("stream_type", mediaData.getStreamtype());
        result.put("license_by", mediaData.getLicenseBy());
        result.put("format", mediaData.getFormat());
        result.put("remote_reference", mediaData.getRemoteReference());
        result.put("is_ugc", mediaData.getIsUGC());
        result.put("is_remote_media", mediaData.getIsRemoteMedia());
        result.put("is_re_live", mediaData.getIsReLive());
        result.put("custom_attributes", mediaData.getCustomAttributes());
        return result;
    }
}
