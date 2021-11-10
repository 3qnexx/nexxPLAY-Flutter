package tv.nexx.flutter.android.platform_view.command;

import java.util.Arrays;
import java.util.List;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTableMethod;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayMethodResult;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

class GetCaptionLanguagesCommand implements DispatchTableMethod<NexxPlayPlatformView, NexxPlayDispatchPayload> {
    private static final GetCaptionLanguagesCommand INSTANCE = new GetCaptionLanguagesCommand();

    private GetCaptionLanguagesCommand() {
    }

    public static GetCaptionLanguagesCommand create() {
        return INSTANCE;
    }

    @Override
    public void dispatch(NexxPlayPlatformView receiver, NexxPlayDispatchPayload payload) {
        final String[] languages = receiver.state().player().getCaptionLanguages();
        final int id = receiver.state().id().numeric();
        final NexxPlayMethodResult result = NexxPlayMethodResult.from(id)
                .put("languages", serialize(languages));
        payload.result().success(result.asMap());
    }

    private List<String> serialize(String[] languages) {
        return Arrays.asList(languages);
    }

}
