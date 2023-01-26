package tv.nexx.flutter.android.platform_view.command;

import java.util.HashMap;
import java.util.Map;

import tv.nexx.android.play.MediaParentData;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class GetCurrentMediaParentCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private GetCurrentMediaParentCommand() {
    }

    public static GetCurrentMediaParentCommand create() {
        return new GetCurrentMediaParentCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final MediaParentData data = receiver.state().player().getCurrentMediaParent();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id)
                .put("media_parent_data", data == null ? null : serialize(data));
        payload.result().success(result.asMap());
    }

    private Map<String, Object> serialize(MediaParentData data) {
        final Map<String, Object> result = new HashMap<>();
        result.put("id", data.getID());
        result.put("global_id", data.getGlobalID());
        result.put("original_domain", data.getOriginalDomain());
        result.put("hash", data.getHash());
        result.put("title", data.getTitle());
        result.put("subtitle", data.getSubtitle());
        result.put("thumb", data.getThumb());
        result.put("stream_type", data.getStreamtype());
        result.put("release_date", data.getReleaseDate());
        result.put("license_by", data.getLicenseBy());
        result.put("channel", data.getChannel());
        result.put("format", data.getFormat());
        result.put("custom_attributes", data.getCustomAttributes());
        return result;
    }
}
