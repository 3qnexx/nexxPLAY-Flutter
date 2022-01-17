import 'package:flutter/foundation.dart';

/// Description of an event, emitted by a player.
///
/// Events could be walked through (inspected) by using a [PlayerEventVisitor]
/// instance.
abstract class PlayerEvent {
  T visit<T>(PlayerEventVisitor<T> visitor);
}

/// Depiction of a player's state type.
enum PlayerState {
  idle,
  preparing,
  buffering,
  playing,
  paused,
  resuming,
  ended
}

/// An object, that can be used to inspect [PlayerEvent] instances.
abstract class PlayerEventVisitor<T> {
  T onPlayerStateChanged(PlayerStateChangeEvent event);

  T onPlayerEvent(DirectPlayerEvent event);
}

/// Ad Hoc Visitor enables you to override only what you need when implementing
/// [PlayerEventVisitor].
/// Returns `null` by default for every override.
mixin AdHocVisitor<T> implements PlayerEventVisitor<T?> {
  @override
  T? onPlayerStateChanged(PlayerStateChangeEvent event) => null;

  @override
  T? onPlayerEvent(DirectPlayerEvent event) => null;
}

/// Event depicting player's state change.
@immutable
class PlayerStateChangeEvent implements PlayerEvent {
  final bool playWhenReady;
  final PlayerState state;

  const PlayerStateChangeEvent(this.state, {required this.playWhenReady});

  @override
  T visit<T>(PlayerEventVisitor<T> visitor) =>
      visitor.onPlayerStateChanged(this);

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is PlayerStateChangeEvent &&
        other.playWhenReady == playWhenReady &&
        other.state == state;
  }

  @override
  int get hashCode => playWhenReady.hashCode ^ state.hashCode;

  @override
  String toString() =>
      'PlayerStateChangeEvent(playWhenReady: $playWhenReady, state: $state)';
}

/// Event containing arbitrary information emitted by the player.
///
/// Contains [NexxEventType], which can be used for better context.
@immutable
class DirectPlayerEvent implements PlayerEvent {
  final Map<String, Object?> _properties;

  Map<String, Object?> get properties => Map.of(_properties);

  const DirectPlayerEvent(this._properties);

  NexxEventType? get type {
    final type = _properties['event'];
    return type == null ? null : type as NexxEventType;
  }

  @override
  T visit<T>(PlayerEventVisitor<T> visitor) => visitor.onPlayerEvent(this);

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is DirectPlayerEvent &&
        mapEquals(other._properties, _properties);
  }

  @override
  int get hashCode => _properties.hashCode;

  @override
  String toString() => 'DirectPlayerEvent(_properties: $_properties)';
}

/// Lists arbitrary event types, which can be emitted by nexxPLAY.
enum NexxEventType {
  playerAdded,
  playerReady,
  changeMedia,
  changePlayPosition,
  changeMediaIntent,
  metadata,
  startSession,
  startPlay,
  startPlayback,
  mainInteraction,
  pause,
  play,
  replay,
  second,
  quarter,
  progress25,
  progress50,
  progress75,
  progress95,
  showHotSpot,
  hideHotSpot,
  showOverlay,
  hideOverlay,
  enterPIP,
  exitPIP,
  enterFullScreen,
  exitFullScreen,
  mute,
  unmute,
  ended,
  payPreviewEnded,
  endedAll,
  error,
  adCalled,
  adStarted,
  adEnded,
  adResumed,
  adError,
  adClicked,
  trickPlay,
  bumperClicked,
}
