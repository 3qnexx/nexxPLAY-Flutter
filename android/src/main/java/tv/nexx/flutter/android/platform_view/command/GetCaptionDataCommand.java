package tv.nexx.flutter.android.platform_view.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.nexx.android.play.NexxPLAY;
import tv.nexx.android.play.logic.Caption;
import tv.nexx.android.play.logic.CaptionData;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class GetCaptionDataCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final GetCaptionDataCommand INSTANCE = new GetCaptionDataCommand();

    private GetCaptionDataCommand() {
    }

    public static GetCaptionDataCommand create() {
        return INSTANCE;
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final String language = payload.call().argument("language");
        final NexxPLAY player = receiver.state().player();
        final Caption[] captions = language == null ? player.getCaptionData() : player.getCaptionData(language);
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id)
                .put("caption_data", serialize(captions));
        payload.result().success(result.asMap());
    }

    private List<Map<String, Object>> serialize(Caption[] captions) {
        final List<Map<String, Object>> result = new ArrayList<>(captions.length);
        for (final Caption caption : captions) {
            result.add(serialize(caption));
        }
        return result;
    }

    private Map<String, Object> serialize(Caption caption) {
        final Map<String, Object> serialized = new HashMap<>();
        serialized.put("id", caption.getID());
        serialized.put("hash", caption.getHash());
        serialized.put("title", caption.getTitle());
        serialized.put("language", caption.getLanguage());
        serialized.put("asset_root", caption.getAssetRoot());
        serialized.put("flag_url", caption.getFlagUrl());
        serialized.put("is_audio_description", caption.getIsAudioDescription());
        serialized.put("data", serialize(caption.getData()));
        return serialized;
    }

    private List<Map<String, Object>> serialize(CaptionData[] data) {
        final List<Map<String, Object>> result = new ArrayList<>(data.length);
        for (final CaptionData captionData : data) {
            result.add(serialize(captionData));
        }
        return result;
    }

    private Map<String, Object> serialize(CaptionData captionData) {
        final Map<String, Object> serialized = new HashMap<>();
        serialized.put("id", captionData.getID());
        serialized.put("caption", captionData.getCaption());
        serialized.put("fromms", captionData.getFromms());
        serialized.put("toms", captionData.getToms());
        return serialized;
    }
}
