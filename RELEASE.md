# Releasing the Plugin

### Overview

The centralized hosting space for Dart / Flutter libraries (packages or plugins) is [pub](https://pub.dev/), which is maintained by Google. nexxPLAY Flutter plugin[ is hosted there, too](https://pub.dev/packages/nexxplay). `omnia.nexx.cloud` serves as the publisher. There is a set of steps to be done so that the new plugin version is released to pub.

### Release names

Usual practices for version names are:
1. Update patch version if the release is about fixes or improvements. Patch version is the `z` number in `x.y.z` format.
2. Update minor version if the release is about functionality changes, like new functionality was added. Minor version is the `y` number in `x.y.z` format.
3. Update major version if the release is about major breaking technical (which will affect the clients) or behavior changes. Major version is the `x` number in `x.y.z` format.

### Plugin Release Steps

1. When the plugin's functionality for release, a release branch should be created in git from the latest wanted commit from `develop`, depicting the new plugin version (e.g. `release/1.2.3`).
2. First commit should be for updating the version in `pubspec.yaml` (message usually follows the format of '`Upgrade version to 1.2.3`').
3. Update the CHANGELOG.md to include the changes incorporated to the new release.
4. Make sure to be logged in to [pub](https://pub.dev/) with a Google account with access to the releases management as `omnia.nexx.cloud` publisher.
5. Run `flutter pub publish --dry-run` to identify and fix RC issues.
6. Run `flutter pub publish` when the RC is ready for release.
7. Merge the `release` branch into `main`.
8. Create a tag with the version name for the `release`-`main` merge commit with the appropriate release description.
9. If there is a deviation between `main` and `develop` in terms of commits (`develop` is further above) - merge the `release` branch into `develop`, too. Otherwise just hard reset `develop` to the updated `main`.
10. Delete the `release` branch.