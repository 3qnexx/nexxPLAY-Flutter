import 'package:flutter/foundation.dart';

/// The NexxPLAYEnvironment object contains global settings for the player
/// object. Except for the `domain` all the settings are optional and have a
/// predefined value. Documentation for all the supported properties is
/// available [here](https://play.docs.nexx.cloud/native-players/nexxplay-for-android#player-environment).
@immutable
class NexxPlayEnvironment {
  final Map<String, Object> _environment;

  const NexxPlayEnvironment(this._environment);

  Map<String, Object> asMap() => _environment;

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is NexxPlayEnvironment &&
        mapEquals(other._environment, _environment);
  }

  @override
  int get hashCode => _environment.hashCode;

  @override
  String toString() => 'NexxPlayEnvironment(_environment: $_environment)';
}

/// Just like the Javascript Player, the Player can be configured by a
/// Configuration Object. This Object will be used in one of the various play()
/// Methods of the SDK. All Options are identical to the general SDK Override
/// Options, you will find [here](https://play.docs.nexx.cloud/integration-enhancements/override-options).
/// If you are looking for the TCF Consent String Management, this is documented
/// [here](https://play.docs.nexx.cloud/integration-enhancements/gdpr-and-tcf-2.0).
@immutable
class NexxPlayConfiguration {
  final Map<String, Object> _configuration;

  const NexxPlayConfiguration(this._configuration);

  Map<String, Object> asMap() => _configuration;

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;

    return other is NexxPlayConfiguration &&
        mapEquals(other._configuration, _configuration);
  }

  @override
  int get hashCode => _configuration.hashCode;

  @override
  String toString() => 'NexxPlayConfiguration(_configuration: $_configuration)';
}
