import 'package:flutter/foundation.dart';

@immutable
class NexxPlayConfiguration {
  final Map<String, Object> _arguments;

  factory NexxPlayConfiguration({
    required String provider,
    required String domainID,
    required String mediaID,
    required String playMode,
    required String dataMode,
    required String exitMode,
    required String mediaSourceType,
    required String streamingFilter,
    required String adType,
    required bool autoplay,
    required bool autoNext,
    required bool disableAds,
    required bool hidePrevNext,
    required bool forcePrevNext,
    required bool startFullscreen,
    required int startPosition,
    required double delay,
  }) {
    final arguments = {
      'provider': provider,
      'domainID': domainID,
      'mediaID': mediaID,
      'playMode': playMode,
      'dataMode': dataMode,
      'exitMode': exitMode,
      'mediaSourceType': mediaSourceType,
      'streamingFilter': streamingFilter,
      'adType': adType,
      'autoplay': autoplay,
      'autoNext': autoNext,
      'disableAds': disableAds,
      'hidePrevNext': hidePrevNext,
      'forcePrevNext': forcePrevNext,
      'startFullscreen': startFullscreen,
      'startPosition': startPosition,
      'delay': delay,
    };
    return NexxPlayConfiguration._(arguments);
  }

  const NexxPlayConfiguration._(this._arguments);

  Map<String, Object> toMap() => Map.of(_arguments);

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is NexxPlayConfiguration &&
      mapEquals(other._arguments, _arguments);
  }

  @override
  int get hashCode => _arguments.hashCode;

  @override
  String toString() => 'NexxPlayConfiguration(_arguments: $_arguments)';
}
