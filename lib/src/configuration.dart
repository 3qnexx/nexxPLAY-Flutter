import 'package:flutter/foundation.dart';

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
