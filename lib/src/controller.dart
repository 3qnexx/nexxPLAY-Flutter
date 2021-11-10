import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:nexxplay/src/configuration.dart';
import 'package:nexxplay/src/event.dart';

abstract class NexxPlayControllerFactory {
  factory NexxPlayControllerFactory() = _MethodChannelNexxPlayControllerFactory;

  NexxPlayController create(String type, int id);
}

abstract class NexxPlayController {
  Future<void> startPlay({
    required String playMode,
    required String mediaID,
    required NexxPlayConfiguration configuration,
  });

  Future<void> startPlayWithGlobalID({
    required String globalID,
    required NexxPlayConfiguration configuration,
  });

  Future<void> startPlayWithRemoteMedia({
    required String playMode,
    required String mediaID,
    required String provider,
    required NexxPlayConfiguration configuration,
  });

  Stream<PlayerEvent> events();

  void dispose();

  Future<void> clearCache();

  Future<void> updateEnvironment({
    required String key,
    required Object value,
  });

  Future<void> updateConfiguration({
    required String key,
    required Object value,
  });

  Future<void> play();

  Future<void> pause();

  Future<void> toggle();

  Future<void> mute();

  Future<void> unmute();

  Future<void> next();

  Future<void> previous();

  Future<void> seekTo(double timeMillis);

  Future<void> seekBy(double timeSeconds);

  Future<void> swapToPosition(int position);

  Future<void> swapToMediaItem({
    required String mediaID,
    String? streamType,
    int startPosition = 0,
    double delay = 0,
  });

  Future<void> swapToGlobalID({
    required String globalID,
    int startPosition = 0,
    double delay = 0,
  });

  Future<void> swapToRemoteMedia({
    required String reference,
    required String provider,
    double delay = 0,
  });
}

class _MethodChannelNexxPlayControllerFactory
    implements NexxPlayControllerFactory {
  const _MethodChannelNexxPlayControllerFactory();

  @override
  NexxPlayController create(String type, int id) {
    final identifier = '${type}_$id';
    final methodChannel = MethodChannel('${identifier}_methods');
    final eventChannel = EventChannel('${identifier}_events');
    return _MethodChannelNexxPlayController(
      methodChannel,
      eventChannel,
      const _PlayerEventFactory(),
    );
  }
}

class _MethodChannelNexxPlayController implements NexxPlayController {
  _MethodChannelNexxPlayController(
    this._methodChannel,
    EventChannel eventChannel,
    _PlayerEventFactory eventFactory,
  ) : _events = eventChannel
            .receiveBroadcastStream()
            .asBroadcastStream()
            .map(eventFactory.fromPlatformInterface);

  @override
  Future<void> startPlay({
    required String playMode,
    required String mediaID,
    required NexxPlayConfiguration configuration,
  }) {
    final playback = _NexxPlayPlaybackConfiguration.normal(
      playMode: playMode,
      mediaID: mediaID,
    );
    return _startPlayer(playback, configuration);
  }

  @override
  Future<void> startPlayWithGlobalID({
    required String globalID,
    required NexxPlayConfiguration configuration,
  }) {
    final playback = _NexxPlayPlaybackConfiguration.global(mediaID: globalID);
    return _startPlayer(playback, configuration);
  }

  @override
  Future<void> startPlayWithRemoteMedia({
    required String playMode,
    required String mediaID,
    required String provider,
    required NexxPlayConfiguration configuration,
  }) {
    final playback = _NexxPlayPlaybackConfiguration.remote(
      mediaID: mediaID,
      playMode: playMode,
      provider: provider,
    );
    return _startPlayer(playback, configuration);
  }

  Future<void> _startPlayer(
    _NexxPlayPlaybackConfiguration playback,
    NexxPlayConfiguration configuration,
  ) =>
      _invokeVoidMapMethod(
        'start',
        {'playback': playback.toMap(), 'configuration': configuration.asMap()},
      );

  @override
  Future<void> clearCache() => _invokeVoidMapMethod('clearCache');

  @override
  Future<void> updateEnvironment({
    required String key,
    required Object value,
  }) =>
      _invokeVoidMapMethod(
        'updateEnvironment',
        <String, dynamic>{'key': key, 'value': value},
      );

  @override
  Future<void> updateConfiguration({
    required String key,
    required Object value,
  }) =>
      _invokeVoidMapMethod(
        'updateConfiguration',
        <String, dynamic>{'key': key, 'value': value},
      );

  @override
  Future<void> play() => _invokeVoidMapMethod('play');

  @override
  Future<void> pause() => _invokeVoidMapMethod('pause');

  @override
  Future<void> toggle() => _invokeVoidMapMethod('toggle');

  @override
  Future<void> mute() => _invokeVoidMapMethod('mute');

  @override
  Future<void> unmute() => _invokeVoidMapMethod('unmute');

  @override
  Future<void> next() => _invokeVoidMapMethod('next');

  @override
  Future<void> previous() => _invokeVoidMapMethod('previous');

  @override
  Future<void> seekTo(double timeMillis) =>
      _invokeVoidMapMethod('seekTo', {'time': timeMillis});

  @override
  Future<void> seekBy(double timeSeconds) =>
      _invokeVoidMapMethod('seekBy', {'time': timeSeconds});

  @override
  Future<void> swapToPosition(int position) =>
      _invokeVoidMapMethod('swapToPosition', {'position': position});

  @override
  Future<void> swapToMediaItem({
    required String mediaID,
    String? streamType,
    int startPosition = 0,
    double delay = 0,
  }) {
    final arguments = {
      'mediaID': mediaID,
      'streamType': streamType,
      'startPosition': startPosition,
      'delay': delay,
    };
    return _invokeVoidMapMethod('swapToMediaItem', arguments);
  }

  @override
  Future<void> swapToGlobalID({
    required String globalID,
    int startPosition = 0,
    double delay = 0,
  }) {
    final arguments = {
      'globalID': globalID,
      'startPosition': startPosition,
      'delay': delay,
    };
    return _invokeVoidMapMethod('swapToGlobalID', arguments);
  }

  @override
  Future<void> swapToRemoteMedia({
    required String reference,
    required String provider,
    double delay = 0,
  }) {
    final arguments = {
      'reference': reference,
      'provider': provider,
      'delay': delay,
    };
    return _invokeVoidMapMethod('swapToRemoteMedia', arguments);
  }

  @override
  Stream<PlayerEvent> events() =>
      _events ??
      (throw StateError('Player was not started yet or was already disposed'));

  @override
  void dispose() => _events = null;

  Future<void> _invokeVoidMapMethod(String name, [Object? arguments]) {
    return _methodChannel
        .invokeMapMethod<String, Object>(name, arguments)
        .then(_throwIfCommonCallResultInvalid);
  }

  void _throwIfCommonCallResultInvalid(Map<String, Object>? result) {
    if (result == null) throw StateError('No result received.');
    if (!_MethodChannelMapResult(result).isArgumentPresent('id', int)) {
      throw StateError('Expected result was not received.');
    }
  }

  final MethodChannel _methodChannel;
  Stream<PlayerEvent>? _events;
}

class _MethodChannelMapResult {
  final Map<String, Object> _raw;

  const _MethodChannelMapResult(this._raw);

  Map<String, Object> raw() => _raw;

  bool isArgumentPresent(String name, Type type) {
    return _raw[name]?.runtimeType == type;
  }

  @override
  String toString() => '_MethodChannelMapResult(_raw: $_raw)';
}

class _NexxPlayPlaybackConfiguration {
  final String _mediaSourceType;
  final String _mediaID;
  final String? _playMode;
  final String? _provider;

  const _NexxPlayPlaybackConfiguration.normal({
    required String playMode,
    required String mediaID,
  })  : _mediaSourceType = 'NORMAL',
        _mediaID = mediaID,
        _playMode = playMode,
        _provider = null;

  const _NexxPlayPlaybackConfiguration.remote({
    required String playMode,
    required String mediaID,
    required String provider,
  })  : _mediaSourceType = 'REMOTE',
        _mediaID = mediaID,
        _playMode = playMode,
        _provider = provider;

  const _NexxPlayPlaybackConfiguration.global({required String mediaID})
      : _mediaSourceType = 'GLOBAL',
        _mediaID = mediaID,
        _playMode = null,
        _provider = null;

  Map<String, Object> toMap() {
    final playMode = _playMode;
    final provider = _provider;
    return {
      'mediaSourceType': _mediaSourceType,
      'mediaID': _mediaID,
      if (playMode != null) 'playMode': playMode,
      if (provider != null) 'provider': provider,
    };
  }
}

@immutable
class _PlayerEventFactory {
  const _PlayerEventFactory();

  PlayerEvent fromPlatformInterface(dynamic payload) {
    final typed = payload as Map<dynamic, dynamic>;
    final casted = typed.cast<String, Object>();
    final type = casted['player_event_type'];
    return _parserMap[type]?.call(this, casted) ?? _parseUnknownEvent(casted);
  }

  PlayerEvent _parseStateChangedEvent(Map<String, Object> event) {
    return PlayerStateChangeEvent(
      _stateMapping[event['state']!]!,
      playWhenReady: event['play_when_ready'] as bool,
    );
  }

  PlayerEvent _parsePlayerEvent(Map<String, Object> typed) {
    final properties = typed['properties'] as Map<dynamic, dynamic>;
    final casted = properties.cast<String, Object?>();
    final eventType = casted['event'];
    if (eventType == null) {
      return DirectPlayerEvent(casted);
    } else {
      final copy = Map.of(casted);
      copy['event'] = _eventMapping[eventType]!;
      return DirectPlayerEvent(copy);
    }
  }

  PlayerEvent _parseUnknownEvent(Map<String, Object> payload) {
    throw ArgumentError.value(
      payload,
      'payload',
      'does not contain a valid "type" property '
          '("player_event_type" or "player_event" are expected)',
    );
  }

  static final _parserMap =
      <String, PlayerEvent Function(_PlayerEventFactory, Map<String, Object>)>{
    'player_state_changed': (player, event) =>
        player._parseStateChangedEvent(event),
    'player_event': (player, event) => player._parsePlayerEvent(event),
  };

  static const _stateMapping = {
    'IDLE': PlayerState.idle,
    'PREPARING': PlayerState.preparing,
    'BUFFERING': PlayerState.buffering,
    'PLAYING': PlayerState.playing,
    'PAUSED': PlayerState.paused,
    'RESUMING': PlayerState.resuming,
    'ENDED': PlayerState.ended,
  };

  static const _eventMapping = {
    'playeradded': NexxEventType.playerAdded,
    'playerready': NexxEventType.playerReady,
    'changemedia': NexxEventType.changeMedia,
    'changeplaypos': NexxEventType.changePlayPosition,
    'changemediaintent': NexxEventType.changeMediaIntent,
    'metadata': NexxEventType.metadata,
    'startsession': NexxEventType.startSession,
    'startplay': NexxEventType.startPlay,
    'startplayback': NexxEventType.startPlayback,
    'maininteraction': NexxEventType.mainInteraction,
    'pause': NexxEventType.pause,
    'play': NexxEventType.play,
    'replay': NexxEventType.replay,
    'second': NexxEventType.second,
    'quarter': NexxEventType.quarter,
    'progress25': NexxEventType.progress25,
    'progress50': NexxEventType.progress50,
    'progress75': NexxEventType.progress75,
    'progress95': NexxEventType.progress95,
    'showhotspot': NexxEventType.showHotSpot,
    'hidehotspot': NexxEventType.hideHotSpot,
    'showoverlay': NexxEventType.showOverlay,
    'hideoverlay': NexxEventType.hideOverlay,
    'enterpip': NexxEventType.enterPIP,
    'exitpip': NexxEventType.exitPIP,
    'enterfullscreen': NexxEventType.enterFullScreen,
    'exitfullscreen': NexxEventType.exitFullScreen,
    'mute': NexxEventType.mute,
    'unmute': NexxEventType.unmute,
    'ended': NexxEventType.ended,
    'paypreviewended': NexxEventType.payPreviewEnded,
    'endedall': NexxEventType.endedAll,
    'error': NexxEventType.error,
    'adcalled': NexxEventType.adCalled,
    'adstarted': NexxEventType.adStarted,
    'adended': NexxEventType.adEnded,
    'adresumed': NexxEventType.adResumed,
    'aderror': NexxEventType.adError,
    'adclicked': NexxEventType.adClicked,
    'trickplay': NexxEventType.trickPlay,
    'bumperclicked': NexxEventType.bumperClicked,
  };
}
