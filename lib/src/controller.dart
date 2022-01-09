import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:nexxplay/src/configuration.dart';
import 'package:nexxplay/src/details.dart';
import 'package:nexxplay/src/event.dart';

abstract class NexxPlayControllerFactory {
  factory NexxPlayControllerFactory() = _MethodChannelNexxPlayControllerFactory;

  NexxPlayController create(String type, int id);
}

abstract class NexxPlayController {
  Stream<PlayerEvent> events();

  void dispose();

  Future<StartResult> startPlay({
    required String playMode,
    required String mediaID,
    required NexxPlayConfiguration configuration,
  });

  Future<StartResult> startPlayWithGlobalID({
    required String globalID,
    required NexxPlayConfiguration configuration,
  });

  Future<StartResult> startPlayWithRemoteMedia({
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
  });

  Future<void> swapToGlobalID({
    required String globalID,
    int startPosition = 0,
    double delay = 0,
  });

  Future<void> swapToRemoteMedia({
    required String reference,
    required String provider,
    String? streamType,
    double delay = 0,
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

  Future<MediaData> getMediaData();

  Future<List<Caption>> getCaptionData([String? language]);

  Future<List<String>> getCaptionLanguages();

  Future<List<String>> getAudioLanguages();

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
  Future<StartResult> startPlay({
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
  Future<StartResult> startPlayWithGlobalID({
    required String globalID,
    required NexxPlayConfiguration configuration,
  }) {
    final playback = _NexxPlayPlaybackConfiguration.global(mediaID: globalID);
    return _startPlayer(playback, configuration);
  }

  @override
  Future<StartResult> startPlayWithRemoteMedia({
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

  Future<StartResult> _startPlayer(
    _NexxPlayPlaybackConfiguration playback,
    NexxPlayConfiguration configuration,
  ) async {
    final arguments = {
      'playback': playback.toMap(),
      'configuration': configuration.asMap()
    };
    final result = await _invokeMapMapMethod('start', arguments);
    return StartResult._from(_MethodChannelMapResult(result));
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
  Future<List<Caption>> getCaptionData([String? language]) async {
    final arguments = language == null ? null : {'language': language};
    final result = await _invokeMapMapMethod('getCaptionData', arguments);
    return _GetCaptionsDataResult.from(_MethodChannelMapResult(result))
        .captions;
  }

  @override
  Future<MediaData> getMediaData() async {
    final result = await _invokeMapMapMethod('getMediaData');
    return _GetMediaDataResult.from(_MethodChannelMapResult(result)).mediaData;
  }

  @override
  Future<List<String>> getCaptionLanguages() async {
    final result = await _invokeMapMapMethod('getCaptionLanguages');
    return _GetLanguagesResult.from(_MethodChannelMapResult(result)).languages;
  }

  @override
  Future<List<String>> getAudioLanguages() async {
    final result = await _invokeMapMapMethod('getAudioLanguages');
    return _GetLanguagesResult.from(_MethodChannelMapResult(result)).languages;
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

@immutable
class StartResult {
  final bool isChromeCastEnabled;
  final String? chromeCastFailureCause;

  const StartResult._({
    required this.isChromeCastEnabled,
    required this.chromeCastFailureCause,
  });

  factory StartResult._from(_MethodChannelMapResult result) {
    final isEnabled = result.get<bool>('chrome_cast_enabled') ?? false;
    final failureCause = result.get<String>('chrome_cast_failure_cause');
    return StartResult._(
      isChromeCastEnabled: isEnabled,
      chromeCastFailureCause: failureCause,
    );
  }

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is StartResult &&
          runtimeType == other.runtimeType &&
          isChromeCastEnabled == other.isChromeCastEnabled &&
          chromeCastFailureCause == other.chromeCastFailureCause;

  @override
  int get hashCode =>
      isChromeCastEnabled.hashCode ^ chromeCastFailureCause.hashCode;

  @override
  String toString() {
    return 'StartResult{isChromeCastEnabled: $isChromeCastEnabled, '
        'chromeCastFailureCause: $chromeCastFailureCause}';
  }
}

class _GetCaptionsDataResult {
  final List<Caption> captions;

  _GetCaptionsDataResult._(this.captions);

  factory _GetCaptionsDataResult.from(_MethodChannelMapResult result) {
    final captionData = result.get<List>('caption_data');
    if (captionData == null) throw ArgumentError.notNull('caption_data');
    return _GetCaptionsDataResult._(_deserializeCaptions(captionData));
  }

  static List<Caption> _deserializeCaptions(List serialized) => serialized
      .where((Object? e) => e != null)
      .map((Object? e) => _deserializeCaption(e as Map))
      .toList();

  static Caption _deserializeCaption(Map serialized) {
    final data = serialized['data'] as List?;
    return Caption(
      id: serialized['id'] as String?,
      hash: serialized['hash'] as String?,
      title: serialized['title'] as String?,
      langauge: serialized['language'] as String?,
      assetRoot: serialized['asset_root'] as String?,
      flagUrl: serialized['flag_url'] as String?,
      isAudioDescription: serialized['is_audio_description'] as int,
      data: data == null ? null : _deserializeCaptionDataList(data),
    );
  }

  static List<CaptionData> _deserializeCaptionDataList(List serialized) =>
      serialized
          .where((Object? e) => e != null)
          .map((Object? e) => _deserializeCaptionData(e as Map))
          .toList();

  static CaptionData _deserializeCaptionData(Map serialized) {
    return CaptionData(
      id: serialized['id'] as int,
      caption: serialized['caption'] as String?,
      fromms: serialized['fromms'] as int,
      toms: serialized['toms'] as int,
    );
  }
}

class _ListLocalMediaResult {
  final List<OfflineMediaResult> media;

  _ListLocalMediaResult._(this.media);

  factory _ListLocalMediaResult.from(_MethodChannelMapResult result) {
    final localMedia = result.get<List>('local_media');
    if (localMedia == null) throw ArgumentError.notNull('local_media');
    return _ListLocalMediaResult._(_deserializeMediaList(localMedia));
  }

  static List<OfflineMediaResult> _deserializeMediaList(List serialized) =>
      serialized
          .where((Object? e) => e != null)
          .map((Object? e) => _deserializeMedia(e as Map))
          .toList();

  static OfflineMediaResult _deserializeMedia(Map serialized) {
    return OfflineMediaResult(
      downloadState: serialized['download_state'] as String?,
      localCover: serialized['local_cover'] as String?,
      offlineReference: serialized['offline_reference'] as String?,
      operationId: serialized['operation_id'] as int,
      created: serialized['created'] as int,
      hash: serialized['hash'] as String?,
      id: serialized['id'] as int,
      parentID: serialized['parent_id'] as int,
      parentHash: serialized['parent_hash'] as String?,
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
      orderHint: serialized['order_hint'] as String?,
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
      languageRaw: serialized['language_raw'] as String?,
      uploaded: serialized['uploaded'] as int,
      videoType: serialized['video_type'] as String?,
      podcastURL: serialized['podcast_url'] as String?,
    );
  }
}

class _GetLanguagesResult {
  final List<String> languages;

  _GetLanguagesResult._(this.languages);

  factory _GetLanguagesResult.from(_MethodChannelMapResult result) {
    final captionData = result.get<List>('languages');
    if (captionData == null) throw ArgumentError.notNull('languages');
    return _GetLanguagesResult._(_deserializeLanguages(captionData));
  }

  static List<String> _deserializeLanguages(List serialized) =>
      serialized.where((Object? e) => e != null).cast<String>().toList();
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

class _GetMediaDataResult {
  final MediaData mediaData;

  _GetMediaDataResult._(this.mediaData);

  factory _GetMediaDataResult.from(_MethodChannelMapResult result) {
    final captionData = result.get<Map>('media_data');
    if (captionData == null) throw ArgumentError.notNull('media_data');
    return _GetMediaDataResult._(_deserializeCaptions(captionData));
  }

  static MediaData _deserializeCaptions(Map serialized) {
    return MediaData(
      playReason: serialized['play_reason'] as String?,
      isPlayingAd: serialized['is_playing_ad'] as int,
      persons: serialized['persons'] as String?,
      domain: serialized['domain'] as int,
      mediaSessionParent: serialized['media_session_parent'] as String?,
      autoplay: serialized['autoplay'] as int,
      studio: serialized['studio'] as String?,
      studioAdRef: serialized['studio_ad_ref'] as String?,
      mediaID: serialized['media_id'] as int,
      hash: serialized['hash'] as String?,
      title: serialized['title'] as String?,
      subtitle: serialized['subtitle'] as String?,
      teaser: serialized['teaser'] as String?,
      description: serialized['description'] as String?,
      channel: serialized['channel'] as String?,
      uploaded: serialized['uploaded'] as int,
      created: serialized['created'] as int,
      orderHint: serialized['order_hint'] as String?,
      isPresentation: serialized['is_presentation'] as int,
      currentCaptionLanguage: serialized['current_caption_language'] as String?,
      currentAudioLanguage: serialized['current_audio_language'] as String?,
      channelAdRef: serialized['channel_ad_ref'] as String?,
      channelId: serialized['channel_id'] as int,
      thumb: serialized['thumb'] as String?,
      thumbABT: serialized['thumb_ABT'] as String?,
      streamType: serialized['stream_type'] as String?,
      runtime: serialized['runtime'] as String?,
      licenseBy: serialized['license_by'] as int,
      currentPlaybackSpeed: serialized['current_playback_speed'] as double,
      isBumper: serialized['is_bumper'] as int,
      isStitched: serialized['is_stitched'] as int,
      orientation: serialized['orientation'] as String?,
      hasAudio: serialized['has_audio'] as int,
      globalID: serialized['global_id'] as int,
      chosenAbVersion: serialized['chosen_ab_version'] as int,
      playbackMode: serialized['playback_mode'] as String?,
      isRemoteMedia: serialized['is_remote_media'] as int,
      remoteReference: serialized['remote_reference'] as String?,
      isStory: serialized['is_story'] as int,
      isSceneSplit: serialized['is_scene_split'] as int,
      currentTime: serialized['current_time'] as double,
      currentDuration: serialized['current_duration'] as double,
      mediaSession: serialized['media_session'] as String?,
      formatId: serialized['format_id'] as int,
      format: serialized['format'] as String?,
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
