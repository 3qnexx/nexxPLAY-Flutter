package tv.nexx.flutter.android.platform_view.command;

import java.util.Arrays;
import java.util.List;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class GetAudioLanguagesCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private GetAudioLanguagesCommand() {
    }

    public static GetAudioLanguagesCommand create() {
        return new GetAudioLanguagesCommand();
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final String[] languages = receiver.state().player().getAudioLanguages();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id)
                .put("languages", serialize(languages));
        payload.result().success(result.asMap());
    }

    private List<String> serialize(String[] languages) {
        return Arrays.asList(languages);
    }

}
