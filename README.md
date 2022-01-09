# nexxPLAY Flutter

## Features

Flutter plugin serves as a wrapper for the Android nexxPLAY, and, combined
with the custom code that the "example" app provides, enables next features:

- nexxPLAY native view wrapped with a widget available for use
- Dynamic configuration support
- Public API, consisting from methods falling under these categories: 'Preparing and Configuring the Player', 'Playback Control', 'Requesting Player Status and Details'
- Player events observation
- Fullscreen mode
- PiP mode

Please note that iOS is not supported by this plugin.

## Integration guide

1. First of all, you will need to add the nexxPLAY dependency to pubspec.yaml from pub.dev of from GitHub. 

2. Secondly, there is a set of Gradle preparations that need to be done:
    1. Exclude META-INF & LICENCE files from the packaging options:
    ```
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
    
    2. Disable minification for nexxPLAY. You can ignore the whole package `tv.nexx.*` or disable the minification completely
    like this:
    ```
    minifyEnabled false
    shrinkResources false
    ```
    Refer to `example/android/app/build.gradle` for more details. 

3. Thirdly, a set of Android native configurations have to be done:
    1. In the Android app's main manifest file, add `tools:replace="android:label"` to the `application` tag.
    Refer to `example/android/app/src/main/AndroidManifest.xml` for more details.
    2. In the Android app's main manifest file, add `android:resizeableActivity="true"` and  `android:supportsPictureInPicture="true"` to the tag of the Activity you are using.
    Refer to `example/android/app/src/main/AndroidManifest.xml` for more details.
    3. In the Activity's code add (or extend) overrides for `onUserLeaveHint` and `onPictureInPictureModeChanged` methods like this:
    ```
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

4. Next, inherit from the AppCompat theme for the Android native app part (required by Chromecast):
    ```
    <style name="NormalTheme" parent="Theme.AppCompat.DayNight.NoActionBar">
        <item name="android:windowBackground">?android:colorBackground</item>
    </style>
   ```
   Ensure that you have the AndroidX AppCompat dependency, a transitive or a direct one (`
   implementation "androidx.appcompat:appcompat:1.4.0"`). Refer to `example/android/app/build.gradle`, `example/android/app/src/main/res/values/styles.xml` and `example/android/app/src/main/res/values-night/styles.xml` for more details. 

5. [OPTIONAL] If you need ChromeCast covered by nexxPLAY, then follow the documentation [here]( 
) with an exception of native code part: to enable CastContext passing to native player, pass `'enableChromeCast': false,` to `NexxPlayEnvironment` constructor's `Map` argument.

6. Lastly, there is a set of things to be done from the Flutter PoV for proper fullscreen and PiP support. Example app's `main.dart` file contains all the documentation necessary for that.

`INTEGRATION_GUIDE` markers were placed all over the documentation for navigation to make the integration process easier.