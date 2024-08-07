## [1.8.0] 26.07.2024
* nexxPLAY Android SDK dependency version is set to 6.3.21
* Example's nexxPLAY Ad Manager dependency version is set to 1.0.21
* KGP is now a part of the example project and the Gradle configuration is updated to the latest version: those are needed to include a set of dependencies otherwise missing in the final APK. README is updated with the information (item #5 in the integration guide).

## [1.7.0] 17.03.2024
* nexxPLAY Android SDK dependency version is set to 6.3.20
* Example's nexxPLAY Ad Manager dependency version is set to 1.0.20
* NexxPlayPlugin (native Android plugin's class) now contains hooks for defining additional configuration entries which will be used for `startPlay*` methods as predefined values. This was introduced as a tool for passing native values to the resulting NexxPlayConfiguration object, such as R.* references.
* Example and documentation were updated to showcase custom notification icon setup.

## [1.6.1] 21.12.2023
* nexxPLAY Android SDK dependency version is set to 6.3.11
* Example's nexxPLAY Ad Manager dependency version is set to 1.0.11
* Compile and Target SDKs were set to 34
* Min SDK was set fo 25
* Project now requires the Java 17 compatibility (check the example's Gradle configuration for details)

## [1.6.0] 08.07.2023
* nexxPLAY Android SDK dependency version is set to 6.3.07
* Example's nexxPLAY Ad Manager dependency version is set to 1.0.05
* [BREAKING CHANGE] `podcastURL` property was removed from both `MediaGeneral` and `OfflineMediaResult`
* Dart version's upper constraint was set to 4.0.0

## [1.5.1] 26.01.2023
* Minor client documentation fixes

## [1.5.0] 10.01.2023
* nexxPLAY Android SDK dependency version is set to 6.3.04 with the Dart API reflecting the underlying changes.
* Handles for including ad manager, shared media session instance and shared chromecast context instance were added into the plugin with the usage documentation updated.

## [1.4.0] 05.07.2022
* nexxPLAY Android SDK dependency version is set to 6.2.22.

## [1.3.0] 21.05.2022

* Fixed support for Flutter 3.
* nexxPLAY Android SDK dependency version is set to 6.2.10.
* `com.google.android.gms.version` property should not be removed
  from `AndroidManifest.xml` anymore.

## [1.2.0] 23.04.2022

* `getCaptionData`, `getCaptionLanguages` and `getAudioLanguages` methods were
  removed.
* `getAudioTracks`, `getCurrentMediaParent` and `getConnectedFiles` methods were
  added.
* `listLocalMedia` method's result does not contain the `language_raw` property
  anymore.
* `getCurrentPlaybackState` method's result does not contain
  the `caption_language` property
  anymore.
* nexxPLAY Android SDK dependency version is set to 6.2.01.

## [1.1.0] 17.01.2022.

* Native Android nexxPLAY version was updated to 6.1.03.
* Local media and offline playback are now supported.
* isCasting, getCurrentMedia and getCurrentPlaybackState methods were added.
* getMediaData method was removed.
* Signatures for `swapTo*` methods were extended with `reason` and
  `showReturnButton` parameters.
* Android's theme is now required to be extended from AndroidX
  `Theme.AppCompat` inheritor (README contains more specifics).
* Several bugfixes intrinsic to native Android nexxPLAY were applied, changelog
  can be found
  [here](https://github.com/3qnexx/nexxPLAY-android/blob/master/readme.md).

## [1.0.2] 24.11.2021.

* Upgrade nexxPlay version to 6.0.2

## [1.0.1] 23.11.2021.

* Upgrade nexxPlay version to 6.0.1

## [1.0.0] 12.11.2021.

* Plugin with the core functionality released.
