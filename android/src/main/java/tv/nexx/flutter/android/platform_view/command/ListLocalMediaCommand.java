package tv.nexx.flutter.android.platform_view.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.nexx.android.play.offline.OfflineMediaResult;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class ListLocalMediaCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private ListLocalMediaCommand() {
    }

    public static ListLocalMediaCommand create() {
        return new ListLocalMediaCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final String streamType = payload.call().argument("streamType");
        final List<OfflineMediaResult> localMedia = receiver.state().player().listLocalMedia(streamType);
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(receiver.state().id().numeric())
                .put("local_media", serialize(localMedia));
        payload.result().success(result.asMap());
    }

    private List<Map<String, Object>> serialize(List<OfflineMediaResult> localMedia) {
        final List<Map<String, Object>> result = new ArrayList<>(localMedia.size());
        for (final OfflineMediaResult media : localMedia) {
            result.add(serialize(media));
        }
        return result;
    }

    private Map<String, Object> serialize(OfflineMediaResult media) {
        final Map<String, Object> serialized = new HashMap<>();
        serialized.put("download_state", media.getDownloadState());
        serialized.put("local_cover", media.getLocalCover());
        serialized.put("offline_reference", media.getOfflineReference());
        serialized.put("operation_id", media.getOperationid());
        serialized.put("created", media.getCreated());
        serialized.put("hash", media.getHash());
        serialized.put("id", media.getID());
        serialized.put("stream_type", media.streamtype);
        serialized.put("gid", media.getGID());
        serialized.put("is_picked", media.getIsPicked());
        serialized.put("is_ugc", media.getIsUGC());
        serialized.put("is_pay", media.getIsPay());
        serialized.put("episode", media.getEpisode());
        serialized.put("season", media.getSeason());
        serialized.put("language", media.getLanguage());
        serialized.put("channel", media.getChannel());
        serialized.put("license_by", media.getLicenseby());
        serialized.put("release_date", media.getReleaseDate());
        serialized.put("order_hint", media.getOrderHint());
        serialized.put("type", media.getType());
        serialized.put("runtime", media.getRuntime());
        serialized.put("subtitle", media.getSubtitle());
        serialized.put("title", media.getTitle());
        serialized.put("teaser", media.getTeaser());
        serialized.put("description", media.getDescription());
        serialized.put("purpose", media.getPurpose());
        serialized.put("slug", media.getSlug());
        serialized.put("format", media.getFormat());
        serialized.put("content_moderation_aspects", media.getContentModerationAspects());
        serialized.put("format_raw", media.getFormat_raw());
        serialized.put("file_version", media.getFileversion());
        serialized.put("occurance", media.getOccurance());
        serialized.put("uploaded", media.getUploaded());
        serialized.put("video_type", media.getVideoType());
        serialized.put("podcast_url", media.getPodcastURL());
        return serialized;
    }
}
