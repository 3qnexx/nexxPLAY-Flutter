import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:nexxplay/nexxplay.dart';

abstract class NexxPlayControllerFactory {
  factory NexxPlayControllerFactory() = _MethodChannelNexxPlayControllerFactory;

  NexxPlayController create(String type, int id);
}

/// Mirror to the native Android nexxPLAY methods.
///
/// An object of this type is
/// created for all corresponding widgets, getting passed via a consumer
/// callback serving as [NexxPlay] initialization argument. Full methods
/// documentation can be found [here](https://play.docs.nexx.cloud/native-players/nexxplay-for-android#public-methods).
/// Events are supported and available via the `events` stream.
abstract class NexxPlayController {
  Stream<PlayerEvent> events();

  void dispose();

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
    String reason = "",
    bool showReturnButton = false,
  });

  Future<void> swapToGlobalID({
    required String globalID,
    int startPosition = 0,
    double delay = 0,
    String reason = "",
    bool showReturnButton = false,
  });

  Future<void> swapToRemoteMedia({
    required String reference,
    required String provider,
    String? streamType,
    double delay = 0,
    String reason = "",
    bool showReturnButton = false,
  });

  Future<void> startDownloadingLocalMedia({
    required String mediaID,
    required String streamType,
    String? provider,
  });

  Future<List<OfflineMediaResult>> listLocalMedia([String? streamType]);

  Future<bool> hasDownloadOfLocalMedia({
    required String mediaID,
    required String streamType,
    String? provider,
  });

  Future<void> removeLocalMedia({
    required String mediaID,
    required String streamType,
    String? provider,
  });

  Future<void> clearLocalMedia([String? streamType]);

  Future<int> diskSpaceUsedForLocalMedia();

  Future<List<AudioTrack>?> getAudioTracks();

  Future<MediaData> getCurrentMedia();

  Future<MediaParentData?> getCurrentMediaParent();

  Future<CurrentPlaybackState> getCurrentPlaybackState();

  Future<List<ConnectedFile>?> getConnectedFiles();

  Future<double> getCurrentTime();

  Future<bool> isPlaying();

  Future<bool> isPlayingAd();

  Future<bool> isMuted();

  Future<bool> isInPiP();

  Future<bool> isCasting();
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
  ) async {
    final arguments = {
      'playback': playback.toMap(),
      'configuration': configuration.asMap()
    };
    return _invokeVoidMapMethod('start', arguments);
  }

  @override
  Future<void> clearCache() => _invokeVoidMapMethod('clearCache');

  @override
  Future<void> updateEnvironment({
    required String key,
    required Object value,
  }) {
    final arguments = <String, dynamic>{'key': key, 'value': value};
    return _invokeVoidMapMethod('updateEnvironment', arguments);
  }

  @override
  Future<void> updateConfiguration({
    required String key,
    required Object value,
  }) {
    final arguments = <String, dynamic>{'key': key, 'value': value};
    return _invokeVoidMapMethod('updateConfiguration', arguments);
  }

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
    String reason = "",
    bool showReturnButton = false,
  }) {
    final arguments = {
      'mediaID': mediaID,
      'streamType': streamType,
      'startPosition': startPosition,
      'delay': delay,
      'reason': reason,
      'showReturnButton': showReturnButton,
    };
    return _invokeVoidMapMethod('swapToMediaItem', arguments);
  }

  @override
  Future<void> swapToGlobalID({
    required String globalID,
    int startPosition = 0,
    double delay = 0,
    String reason = "",
    bool showReturnButton = false,
  }) {
    final arguments = {
      'globalID': globalID,
      'startPosition': startPosition,
      'delay': delay,
      'reason': reason,
      'showReturnButton': showReturnButton,
    };
    return _invokeVoidMapMethod('swapToGlobalID', arguments);
  }

  @override
  Future<void> swapToRemoteMedia({
    required String reference,
    required String provider,
    String? streamType,
    double delay = 0,
    String reason = "",
    bool showReturnButton = false,
  }) {
    final arguments = {
      'reference': reference,
      'streamType': streamType,
      'provider': provider,
      'delay': delay,
      'reason': reason,
      'showReturnButton': showReturnButton,
    };
    return _invokeVoidMapMethod('swapToRemoteMedia', arguments);
  }

  @override
  Future<void> startDownloadingLocalMedia({
    required String mediaID,
    required String streamType,
    String? provider,
  }) {
    final arguments = {
      'mediaID': mediaID,
      'streamType': streamType,
      if (provider != null) 'provider': provider,
    };
    return _invokeVoidMapMethod('startDownloadingLocalMedia', arguments);
  }

  @override
  Future<List<OfflineMediaResult>> listLocalMedia([String? streamType]) async {
    final arguments = {if (streamType != null) 'streamType': streamType};
    final result = await _invokeMapMapMethod('listLocalMedia', arguments);
    return _ListLocalMediaResult.from(_MethodChannelMapResult(result)).media;
  }

  @override
  Future<bool> hasDownloadOfLocalMedia({
    required String mediaID,
    required String streamType,
    String? provider,
  }) async {
    final arguments = {
      'mediaID': mediaID,
      'streamType': streamType,
      if (provider != null) 'provider': provider,
    };
    return _invokeBoolMapMethod('hasDownloadOfLocalMedia', arguments);
  }

  @override
  Future<void> removeLocalMedia({
    required String mediaID,
    required String streamType,
    String? provider,
  }) {
    final arguments = {
      'mediaID': mediaID,
      'streamType': streamType,
      if (provider != null) 'provider': provider,
    };
    return _invokeVoidMapMethod('removeLocalMedia', arguments);
  }

  @override
  Future<void> clearLocalMedia([String? streamType]) {
    final arguments = {if (streamType != null) 'streamType': streamType};
    return _invokeVoidMapMethod('clearLocalMedia', arguments);
  }

  @override
  Future<int> diskSpaceUsedForLocalMedia() async {
    final result = await _invokeMapMapMethod('diskSpaceUsedForLocalMedia');
    return _PrimitiveResult<int>.from(_MethodChannelMapResult(result)).result;
  }

  @override
  Future<List<AudioTrack>?> getAudioTracks() async {
    final result = await _invokeMapMapMethod('getAudioTracks');
    return _GetAudioTracksResult.from(_MethodChannelMapResult(result))
        .audioTracks;
  }

  @override
  Future<MediaData> getCurrentMedia() async {
    final result = await _invokeMapMapMethod('getCurrentMedia');
    return _GetCurrentMediaResult.from(_MethodChannelMapResult(result))
        .mediaData;
  }

  @override
  Future<MediaParentData?> getCurrentMediaParent() async {
    final result = await _invokeMapMapMethod('getCurrentMediaParent');
    return _GetCurrentMediaParentResult.from(_MethodChannelMapResult(result))
        .mediaParentData;
  }

  @override
  Future<List<ConnectedFile>?> getConnectedFiles() async {
    final result = await _invokeMapMapMethod('getConnectedFiles');
    return _GetConnectedFiles.from(_MethodChannelMapResult(result))
        .connectedFiles;
  }

  @override
  Future<CurrentPlaybackState> getCurrentPlaybackState() async {
    final result = await _invokeMapMapMethod('getCurrentPlaybackState');
    return _GetCurrentPlaybackStateResult.from(_MethodChannelMapResult(result))
        .playbackState;
  }

  @override
  Future<double> getCurrentTime() async {
    final result = await _invokeMapMapMethod('getCurrentTime');
    return _GetCurrentTimeResult.from(_MethodChannelMapResult(result)).time;
  }

  @override
  Future<bool> isPlaying() => _invokeBoolMapMethod('isPlaying');

  @override
  Future<bool> isPlayingAd() => _invokeBoolMapMethod('isPlayingAd');

  @override
  Future<bool> isMuted() => _invokeBoolMapMethod('isMuted');

  @override
  Future<bool> isInPiP() => _invokeBoolMapMethod('isInPiP');

  @override
  Future<bool> isCasting() => _invokeBoolMapMethod('isCasting');

  @override
  Stream<PlayerEvent> events() =>
      _events ??
      (throw StateError('Player was not started yet or was already disposed'));

  @override
  void dispose() => _events = null;

  Future<bool> _invokeBoolMapMethod(
    String name, [
    Object? arguments,
  ]) async {
    final result = await _invokeMapMapMethod(name, arguments);
    return _PrimitiveResult<bool>.from(_MethodChannelMapResult(result)).result;
  }

  Future<Map<String, Object>> _invokeMapMapMethod(String name,
      [Object? arguments]) async {
    final result =
        await _methodChannel.invokeMapMethod<String, Object>(name, arguments);
    if (result == null) {
      throw StateError('No response was received from the native part');
    }
    _throwIfCommonCallResultInvalid(result);
    return result;
  }

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

  T? get<T>(String name) {
    final raw = _raw[name];
    return raw == null
        ? null
        : raw is T
            ? raw as T
            : (throw ArgumentError(
                'Value is not of the expected type $T: $raw'));
  }

  @override
  String toString() => '_MethodChannelMapResult(_raw: $_raw)';
}

class _ListLocalMediaResult {
  final List<OfflineMediaResult> media;

  _ListLocalMediaResult._(this.media);

  factory _ListLocalMediaResult.from(_MethodChannelMapResult result) {
    final localMedia = result.get<List<dynamic>>('local_media');
    if (localMedia == null) throw ArgumentError.notNull('local_media');
    return _ListLocalMediaResult._(_deserializeMediaList(localMedia));
  }

  static List<OfflineMediaResult> _deserializeMediaList(
    List<dynamic> serialized,
  ) {
    return serialized
        .where((Object? e) => e != null)
        .map((Object? e) => _deserializeMedia(e as Map))
        .toList();
  }

  static OfflineMediaResult _deserializeMedia(
    Map<dynamic, dynamic> serialized,
  ) {
    return OfflineMediaResult(
      downloadState: serialized['download_state'] as String?,
      localCover: serialized['local_cover'] as String?,
      offlineReference: serialized['offline_reference'] as String?,
      operationId: serialized['operation_id'] as int,
      created: serialized['created'] as int,
      hash: serialized['hash'] as String?,
      id: serialized['id'] as int,
      streamType: serialized['stream_type'] as String?,
      gid: serialized['gid'] as int,
      isPicked: serialized['is_picked'] as int,
      isUGC: serialized['is_ugc'] as int,
      isPay: serialized['is_pay'] as int,
      episode: serialized['episode'] as int,
      season: serialized['season'] as int,
      language: serialized['language'] as String?,
      channel: serialized['channel'] as int,
      licenseBy: serialized['license_by'] as int,
      releaseDate: serialized['release_date'] as int,
      type: serialized['type'] as String?,
      runtime: serialized['runtime'] as String?,
      subtitle: serialized['subtitle'] as String?,
      title: serialized['title'] as String?,
      teaser: serialized['teaser'] as String?,
      description: serialized['description'] as String?,
      purpose: serialized['purpose'] as String?,
      slug: serialized['slug'] as String?,
      format: serialized['format'] as String?,
      contentModerationAspects:
          serialized['content_moderation_aspects'] as String?,
      formatRaw: serialized['format_raw'] as int,
      fileVersion: serialized['file_version'] as int,
      occurance: serialized['occurance'] as int,
      uploaded: serialized['uploaded'] as int,
      videoType: serialized['video_type'] as String?,
      forKids: serialized['for_kids'] as int,
      containerPurpose: serialized['container_purpose'] as String?,
      audioType: serialized['audio_type'] as String?,
    );
  }
}

class _GetCurrentTimeResult {
  final double time;

  _GetCurrentTimeResult._(this.time);

  factory _GetCurrentTimeResult.from(_MethodChannelMapResult result) {
    final time = result.get<double>('time');
    if (time == null) throw ArgumentError.notNull('time');
    return _GetCurrentTimeResult._(time);
  }
}

class _PrimitiveResult<T> {
  final T result;

  _PrimitiveResult._(this.result);

  factory _PrimitiveResult.from(_MethodChannelMapResult result) {
    final primitive = result.get<T>('result');
    if (primitive == null) throw ArgumentError.notNull('result');
    return _PrimitiveResult._(primitive);
  }
}

class _GetAudioTracksResult {
  final List<AudioTrack>? audioTracks;

  _GetAudioTracksResult._(this.audioTracks);

  factory _GetAudioTracksResult.from(_MethodChannelMapResult result) {
    final data = result.get<List<dynamic>?>('audio_tracks');
    return _GetAudioTracksResult._(data == null ? null : _deserialize(data));
  }

  static List<AudioTrack> _deserialize(List<dynamic> serialized) =>
      serialized.map((dynamic e) => _deserializeTrack(e as Map)).toList();

  static AudioTrack _deserializeTrack(Map<dynamic, dynamic> serialized) {
    return AudioTrack(
      language: serialized['language'] as String?,
      role: serialized['role'] as String?,
    );
  }
}

class _GetConnectedFiles {
  final List<ConnectedFile>? connectedFiles;

  _GetConnectedFiles._(this.connectedFiles);

  factory _GetConnectedFiles.from(_MethodChannelMapResult result) {
    final data = result.get<List<dynamic>?>('connected_files');
    return _GetConnectedFiles._(data == null ? null : _deserialize(data));
  }

  static List<ConnectedFile> _deserialize(List<dynamic> serialized) =>
      serialized.map((dynamic e) => _deserializeTrack(e as Map)).toList();

  static ConnectedFile _deserializeTrack(Map<dynamic, dynamic> serialized) {
    return ConnectedFile(
      id: serialized["id"] as int?,
      gid: serialized["global_id"] as int?,
      hash: serialized["hash"] as String?,
      title: serialized["title"] as String?,
      channel: serialized["channel"] as int?,
      format: serialized["format"] as int?,
      extension: serialized["extension"] as String?,
      fileFormat: serialized["file_format"] as String?,
      mimeType: serialized["mime_type"] as String?,
      fileSize: serialized["file_size"] as int?,
      url: serialized["url"] as String?,
    );
  }
}

class _GetCurrentMediaResult {
  final MediaData mediaData;

  _GetCurrentMediaResult._(this.mediaData);

  factory _GetCurrentMediaResult.from(_MethodChannelMapResult result) {
    final data = result.get<Map<dynamic, dynamic>>('media_data');
    if (data == null) throw ArgumentError.notNull('media_data');
    return _GetCurrentMediaResult._(_deserialize(data));
  }

  static MediaData _deserialize(Map<dynamic, dynamic> serialized) => MediaData(
        remoteReference: serialized['remote_reference'] as String?,
        id: serialized['id'] as int,
        gid: serialized['global_id'] as int,
        hash: serialized['hash'] as String?,
        title: serialized['title'] as String?,
        subtitle: serialized['subtitle'] as String?,
        channel: serialized['channel'] as int,
        studio: serialized['studio'] as int,
        thumb: serialized['thumb'] as String?,
        streamtype: serialized['stream_type'] as String?,
        licenseBy: serialized['license_by'] as int,
        originalDomain: serialized['original_domain'] as int,
        format: serialized['format'] as int,
        isUGC: serialized['is_ugc'] as bool,
        releaseDate: serialized['release_date'] as int,
        remoteProvider: serialized['remoteProvider'] as String?,
        isForKids: serialized['is_for_kids'] as bool,
        isPanorama: serialized['is_panorama'] as bool,
        isHDR: serialized['is_hdr'] as bool,
        isPay: serialized['is_pay'] as bool,
        isPicked: serialized['is_picked'] as bool,
        customAttributes: serialized['custom_attributes'] as Map?,
      );
}

class _GetCurrentMediaParentResult {
  final MediaParentData? mediaParentData;

  _GetCurrentMediaParentResult._(this.mediaParentData);

  factory _GetCurrentMediaParentResult.from(_MethodChannelMapResult result) {
    final data = result.get<Map<dynamic, dynamic>>('media_parent_data');
    return _GetCurrentMediaParentResult._(
        data == null ? null : _deserialize(data));
  }

  static MediaParentData _deserialize(Map<dynamic, dynamic> serialized) {
    return MediaParentData(
      id: serialized['id'] as int,
      gid: serialized['global_id'] as int,
      hash: serialized['hash'] as String?,
      title: serialized['title'] as String?,
      subtitle: serialized['subtitle'] as String?,
      thumb: serialized['thumb'] as String?,
      streamtype: serialized['stream_type'] as String?,
      originalDomain: serialized['original_domain'] as int,
      releaseDate: serialized['release_date'] as int,
      licenseBy: serialized['license_by'] as int,
      channel: serialized['channel'] as int,
      format: serialized['format'] as int,
      customAttributes: serialized['custom_attributes'] as Map,
    );
  }
}

class _GetCurrentPlaybackStateResult {
  final CurrentPlaybackState playbackState;

  _GetCurrentPlaybackStateResult._(this.playbackState);

  factory _GetCurrentPlaybackStateResult.from(_MethodChannelMapResult result) {
    final captionData = result.get<Map<dynamic, dynamic>>('playback_state');
    if (captionData == null) throw ArgumentError.notNull('playback_state');
    return _GetCurrentPlaybackStateResult._(_deserialize(captionData));
  }

  static CurrentPlaybackState _deserialize(Map<dynamic, dynamic> serialized) {
    return CurrentPlaybackState(
      audioLanguage: serialized["audio_language"] as String?,
      mediaSession: serialized["media_session"] as String?,
      elapsedTime: serialized["elapsed_time"] as double,
      currentTime: serialized["current_time"] as double,
      abTestVersion: serialized["ab_test_version"] as int,
      duration: serialized["duration"] as double,
      playReason: serialized["play_reason"] as String?,
      isAutoPlay: serialized["is_auto_play"] as bool,
      isPlayingAd: serialized["is_playing_ad"] as bool,
      isMuted: serialized["is_muted"] as bool,
      isLocalMedia: serialized["is_local_media"] as bool,
      isInPiP: serialized["is_in_pip"] as bool,
      isCasting: serialized["is_casting"] as bool,
      canBeCommented: serialized["can_be_commented"] as bool,
      isInFullscreen: serialized["is_in_fullscreen"] as bool,
      isStitched: serialized["is_stitched"] as bool,
      isInPopOut: serialized["is_in_pop_out"] as bool,
      textTrackLanguage: serialized["text_track_language"] as String?,
      playbackMode: serialized["playback_mode"] as String?,
      protection: serialized["protection"] as String?,
      steamingFilter: serialized["steaming_filter"] as String?,
      protocol: serialized["protocol"] as String?,
      codec: serialized["codec"] as String?,
      playbackState: serialized["playback_state"] as String?,
      isPlaying: serialized["is_playing"] as bool,
      isHDR: serialized["is_hdr"] as bool,
      isWaitingForPremiere: serialized["is_waiting_for_premiere"] as bool,
      isReLive: serialized["is_re_live"] as bool,
    );
  }
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
    return PlayerStateChangeEvent(_stateMapping[event['state']!]!);
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
