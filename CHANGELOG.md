## [1.2.0] 23.04.2022

* `getCaptionData`, `getCaptionLanguages` and `getAudioLanguages` methods were removed.
* `getAudioTracks`, `getCurrentMediaParent` and `getConnectedFiles` methods were added.
* `listLocalMedia` method's result does not contain the `language_raw` property anymore.
* `getCurrentPlaybackState` method's result does not contain the `caption_language` property
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
* Several bugfixes intrinsic to native Android nexxPLAY were applied, changelog can be found
  [here](https://github.com/3qnexx/nexxPLAY-android/blob/master/readme.md).

## [1.0.2] 24.11.2021.

* Upgrade nexxPlay version to 6.0.2

## [1.0.1] 23.11.2021.

* Upgrade nexxPlay version to 6.0.1

## [1.0.0] 12.11.2021.

* Plugin with the core functionality released.
