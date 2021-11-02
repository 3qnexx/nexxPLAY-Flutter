import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:nexxplay/src/event.dart';

abstract class NexxPlayControllerFactory {
  factory NexxPlayControllerFactory() =
      _MethodChannelNexxPlayControllerFactory;

  NexxPlayController create(String type, int id);
}

abstract class NexxPlayController {
  Future<bool> start();

  Stream<PlayerEvent> events();

  void dispose();
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
      this._methodChannel, this._eventChannel, this._eventFactory);

  @override
  Future<bool> start() async {
    final result =
        await _methodChannel.invokeMapMethod<String, Object>('start');
    if (result == null) throw ArgumentError.notNull('result');
    final typed = _StartResult(_MethodChannelMapResult(result));
    if (typed.isSuccessful()) {
      _events = _eventChannel
          .receiveBroadcastStream()
          .asBroadcastStream()
          .map(_eventFactory.fromPlatformInterface);
    }
    return typed.isSuccessful();
  }

  @override
  Stream<PlayerEvent> events() =>
      _events ??
      (throw StateError('Player was not started yet or was already disposed'));

  @override
  void dispose() => _events = null;

  final _PlayerEventFactory _eventFactory;
  final MethodChannel _methodChannel;
  final EventChannel _eventChannel;
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

@immutable
class _StartResult {
  final Map<String, Object> _raw;

  factory _StartResult(_MethodChannelMapResult result) {
    if (!result.isArgumentPresent('id', int)) {
      throw ArgumentError.value(result, 'result', 'Result does not contain ID');
    }
    if (!result.isArgumentPresent('started', bool)) {
      throw ArgumentError.value(
          result, 'result', 'Result does not contain success flag');
    }
    return _StartResult._(result.raw());
  }

  const _StartResult._(this._raw);

  bool isSuccessful() {
    return _raw['started'] as bool;
  }

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is _StartResult && mapEquals(other._raw, _raw);
  }

  @override
  int get hashCode => _raw.hashCode;

  @override
  String toString() => '_StartResult(_raw: $_raw)';
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
