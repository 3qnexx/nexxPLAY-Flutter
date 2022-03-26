package tv.nexx.flutter.android.platform_view.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.nexx.android.play.ConnectedFile;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class GetConnectedFilesCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private GetConnectedFilesCommand() {
    }

    public static GetConnectedFilesCommand create() {
        return new GetConnectedFilesCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final ConnectedFile[] data = receiver.state().player().getConnectedFiles();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id)
                .put("connected_files", data == null ? null : serialize(data));
        payload.result().success(result.asMap());
    }

    private List<Map<String, Object>> serialize(ConnectedFile[] data) {
        final List<Map<String, Object>> result = new ArrayList<>();
        for (final ConnectedFile file : data) {
            final Map<String, Object> object = new HashMap<>();
            object.put("id", file.getID());
            object.put("global_id", file.getGID());
            object.put("hash", file.getHash());
            object.put("title", file.getTitle());
            object.put("channel", file.getChannel());
            object.put("extension", file.getExtension());
            object.put("file_format", file.getFileFormat());
            object.put("file_size", file.getFileSize());
            object.put("mime_type", file.getMimeType());
            object.put("format", file.getFormat());
            object.put("url", file.getURL());
            result.add(object);
        }
        return result;
    }
}
