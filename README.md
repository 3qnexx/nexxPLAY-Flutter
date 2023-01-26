# nexxPLAY Flutter

## Features

Flutter plugin serves as a wrapper for the Android nexxPLAY, and, combined with the custom code that the "example" app provides, enables next features:
- nexxPLAY native view wrapped with a widget available for use
- Dynamic configuration support
- Public API, consisting from methods falling under these categories: 'Preparing and Configuring the Player', 'Playback Control', 'Requesting Player Status and Details'
- Player events observation
- Fullscreen mode
- PiP mode
Please note that iOS is not supported by this plugin.

## Integration guide
1. First of all, nexxPLAY dependency has to be included into pubspec.yaml.

2. Secondly, there is a set of Gradle preparations that need to be done:

    2.1. Exclude META-INF & LICENCE files from the packaging options:
    ```groovy
    packagingOptions {
	    exclude 'META-INF/ASL2.0'
	    exclude 'META-INF/LICENSE'
	    exclude 'META-INF/LICENSE.txt'
	    exclude 'LICENSE.txt'
	    exclude 'META-INF/license.txt'
	    exclude 'META-INF/NOTICE'
	    exclude 'META-INF/DEPENDENCIES'
	    exclude 'META-INF/NOTICE.txt'
	    exclude 'META-INF/notice.txt'
    }
    ```
    Refer to `example/android/app/build.gradle` for more details.

    2.2. Minification has to be disabled. Either the native nexxPLAY package can be excluded by the minifier tool (`tv.nexx.*`) or the whole process could be disabled completely like this:
    ```groovy
    minifyEnabled false
    shrinkResources false
    ```
    Refer to `example/android/app/build.gradle` for more details.

3. Thirdly, a set of Android native configurations have to be done:

    3.1. In the Android app's main manifest file, add `tools:replace="android:label"` to the `application` tag.
    Refer to `example/android/app/src/main/AndroidManifest.xml` for more details.

    3.2. In the Android app's main manifest file, add `android:resizeableActivity="true"` and `android:supportsPictureInPicture="true"` to the tag of the Activity you are using. Refer to `example/android/app/src/main/AndroidManifest.xml` for more details.
    ```
    Refer to `example/android/app/src/main/AndroidManifest.xml` for more details.

    3.3. In the Activity's code add (or extend) overrides for `onUserLeaveHint` and `onPictureInPictureModeChanged` methods like this:
    ```java
    public class MainActivity extends FlutterActivity {
        // ...
        @Override
        public void onUserLeaveHint() {
            super.onUserLeaveHint();
            NexxPlugin.post(OnUserLeaveHintEvent.create());
        }
    
        @Override
        public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
            super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
            NexxPlugin.post(OnPIPModeChangedEvent.create(isInPictureInPictureMode, newConfig));
        }
    }
    ```
    Refer to `example/android/app/src/main/java/tv/nexx/flutter/android_example/MainActivity.java` for more details.
    
    Kotlin version:
    ```kotlin
    class MainActivity : FlutterActivity {
        override fun onUserLeaveHint() {
            super.onUserLeaveHint()
            NexxPlugin.post(OnUserLeaveHintEvent.create())
        }

        override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration) {
            super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
            NexxPlugin.post(OnPIPModeChangedEvent.create(isInPictureInPictureMode,newConfig))
        }
    }
    ```

4. Next, inherit from the AppCompat theme for the Android native app part (required by ChromeCast, even if you don't use it, unfortunately):
```xml
<style name="NormalTheme" parent="Theme.AppCompat.DayNight.NoActionBar">
    <item name="android:windowBackground">?android:colorBackground</item>
</style>
```
Ensure that you have the AndroidX AppCompat dependency, a transitive or a direct one (`
implementation "androidx.appcompat:appcompat:1.4.0"`). Refer to `example/android/app/build.gradle`, `example/android/app/src/main/res/values/styles.xml` and `example/android/app/src/main/res/values-night/styles.xml` for more details.

5. Lastly, there is a set of things to be done from the Flutter PoV for proper fullscreen and PiP support. Example app's `main.dart` file contains all the documentation necessary for that. `INTEGRATION_GUIDE` markers were placed all over the documentation for navigation to make the integration process easier.

6. If it's needed to share a Android's MediaSessionCompat instance for all the native nexxPLAY instances, then:

    6.1. In the `build.gradle`'s `dependencies` block, the following line should be present: `implementation "androidx.media:media:1.6.0"`. Refer to `example/android/app/build.gradle` for more details.

    6.2. In the host Activity class code, `protected void onCreate(@Nullable Bundle savedInstanceState)` method should be overriden (or, for Kotlin, `protected onCreate(@Nullable savedInstanceState: Bundle)`). Refer to `example/android/app/src/main/java/tv/nexx/flutter/android_example/MainActivity.java` for more details.

    6.3. Inside the `onCreate(Bundle)` method, the following lines should be present:
    ```java
    final MediaSessionCompat mediaSession = new MediaSessionCompat(getApplicationContext(), getPackageName());
    NexxPlayPlugin.addEnvironmentConfigurationEntry(NexxPlayPlugin.KEY_MEDIA_SESSION, mediaSession);
    ```
    Or, for Kotlin:
    ```kotlin
    val mediaSession = MediaSessionCompat(applicationContext, packageName)
    NexxPlayPlugin.addEnvironmentConfigurationEntry(NexxPlayPlugin.KEY_MEDIA_SESSION, mediaSession)
    ```
    The following import has to be present:
    ```java
    import android.support.v4.media.session.MediaSessionCompat;
    ```
    For the Kotlin version, omit the ending semicolon.

7. If it's needed to include ads support (as an intrinsic NexxPlayAdManager instance attached to each Android native player instance), then:

    7.1. In the `build.gradle`'s `dependencies` block, the following line should be present: `implementation "tv.nexx.android:admanager:1.0.02"`. Refer to `example/android/app/build.gradle` for more details.

    7.2. In the host Activity class code, `protected void onCreate(@Nullable Bundle savedInstanceState)` method should be overriden (or, for Kotlin, `protected onCreate(@Nullable savedInstanceState: Bundle)`). Refer to `example/android/app/src/main/java/tv/nexx/flutter/android_example/MainActivity.java` for more details.

    7.3. Inside the `onCreate(Bundle)` method, the following lines should be present:
    ```java
    NexxPlayPlugin.addEnvironmentConfigurationEntry(NexxPlayPlugin.KEY_AD_MANAGER, NexxPlayAdManager::new);
    ```
    Or, for Kotlin:
    ```kotlin
    NexxPlayPlugin.addEnvironmentConfigurationEntry(NexxPlayPlugin.KEY_AD_MANAGER, ::NexxPlayAdManager)
    ```
    The following import has to be present:
    ```java
    import tv.nexx.android.admanager.NexxPlayAdManager;
    ```
    For the Kotlin version, omit the ending semicolon.
8. If it's needed to include Chromecast support, then:

    8.1. The next [native integration guide](https://play.docs.nexx.cloud/native-players/nexxplay-for-android#chromecast) items have to be ensured to reproduced.

    8.2. Application's activity should extend FlutterFragmentActivity or it's inheritor.

    8.3. When resolving the CastContext instance as described by the native integration guide, a modification has to be applied so the CastContext instance is be included into the plugin's configuration:
    ```java
    CastContext.getSharedInstance(this, Executors.newSingleThreadExecutor())
        .addOnSuccessListener(castContext ->  NexxPlayPlugin.addEnvironmentConfigurationEntry(NexxPlayPlugin.KEY_CAST_CONTEXT, castContext))
        .addOnFailureListener(exception ->  Log.e("nexxPLAY", "Could not resolve CastContext", exception));
    ```
    Or, for Kotlin:
    ```kotlin
    CastContext.getSharedInstance(this, Executors.newSingleThreadExecutor())
    .addOnSuccessListener { NexxPlayPlugin.addEnvironmentConfigurationEntry(NexxPlayPlugin.KEY_CAST_CONTEXT, it) }
    .addOnFailureListener { Log.e("nexxPLAY", "Could not resolve CastContext", it) }
    ```
    The following imports have to be present:
    ```java
    import com.google.android.gms.cast.framework.CastContext;
    import java.util.concurrent.Executors;
    import android.util.Log;
    ```
    For the Kotlin version, omit the ending semicolons.