import 'package:flutter/foundation.dart';

abstract class PlayerEvent {
  T visit<T>(PlayerEventVisitor<T> visitor);
}

enum PlayerState {
  idle,
  preparing,
  buffering,
  playing,
  paused,
  resuming,
  ended
}

abstract class PlayerEventVisitor<T> {
  T onPlayerStateChanged(PlayerState state, {required bool playWhenReady});

  T onPlayerEvent(Map<String, Object?> properties);
}

@immutable
class PlayerStateChangeEvent implements PlayerEvent {
  final bool playWhenReady;
  final PlayerState state;

  const PlayerStateChangeEvent(this.state, {required this.playWhenReady});

  @override
  T visit<T>(PlayerEventVisitor<T> visitor) =>
      visitor.onPlayerStateChanged(state, playWhenReady: playWhenReady);

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

@immutable
class DirectPlayerEvent implements PlayerEvent {
  final Map<String, Object?> _properties;

  Map<String, Object?> get properties => Map.of(_properties);

  const DirectPlayerEvent(this._properties);

  @override
  T visit<T>(PlayerEventVisitor<T> visitor) =>
      visitor.onPlayerEvent(properties);

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
