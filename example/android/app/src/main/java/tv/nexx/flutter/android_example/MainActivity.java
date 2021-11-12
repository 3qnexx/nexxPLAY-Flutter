package tv.nexx.flutter.android_example;

import android.content.res.Configuration;

import io.flutter.embedding.android.FlutterActivity;
import tv.nexx.flutter.android.NexxPlayPlugin;
import tv.nexx.flutter.android.android.event.OnPIPModeChangedEvent;
import tv.nexx.flutter.android.android.event.OnUserLeaveHintEvent;

public class MainActivity extends FlutterActivity {

    @Override
    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        NexxPlayPlugin.post(OnUserLeaveHintEvent.create());
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        NexxPlayPlugin.post(OnPIPModeChangedEvent.create(isInPictureInPictureMode, newConfig));
    }
}
