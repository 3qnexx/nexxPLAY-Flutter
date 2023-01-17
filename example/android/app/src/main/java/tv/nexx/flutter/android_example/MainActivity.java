package tv.nexx.flutter.android_example;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterActivity;
import tv.nexx.android.admanager.NexxPlayAdManager;
import tv.nexx.flutter.android.NexxPlayPlugin;
import tv.nexx.flutter.android.android.event.OnPIPModeChangedEvent;
import tv.nexx.flutter.android.android.event.OnUserLeaveHintEvent;

public class MainActivity extends FlutterActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MediaSessionCompat mediaSession = new MediaSessionCompat(getApplicationContext(), getPackageName());
        NexxPlayPlugin.addEnvironmentConfigurationEntry(NexxPlayPlugin.KEY_MEDIA_SESSION, mediaSession);
        NexxPlayPlugin.addEnvironmentConfigurationEntry(NexxPlayPlugin.KEY_AD_MANAGER, NexxPlayAdManager::new);
    }

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
