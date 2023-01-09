package tv.nexx.flutter.android.platform_view.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.nexx.android.play.AudioTrack;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class GetAudioTracksCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private GetAudioTracksCommand() {
    }

    public static GetAudioTracksCommand create() {
        return new GetAudioTracksCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final AudioTrack[] data = receiver.state().player().getAudioTracks();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id)
                .put("audio_tracks", data == null ? null : serialize(data));
        payload.result().success(result.asMap());
    }

    private List<Map<String, Object>> serialize(AudioTrack[] data) {
        final List<Map<String, Object>> result = new ArrayList<>();
        for (final AudioTrack track : data) {
            final Map<String, Object> object = new HashMap<>();
            object.put("language", track.getLanguage());
            object.put("role", track.getRole());
            result.add(object);
        }
        return result;
    }
}
