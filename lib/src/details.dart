import 'package:flutter/foundation.dart';

/// Description of an audio track.
@immutable
class AudioTrack {
  final String? language;
  final int isAudioDescription;

  const AudioTrack({
    required this.language,
    required this.isAudioDescription,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is AudioTrack &&
          runtimeType == other.runtimeType &&
          language == other.language &&
          isAudioDescription == other.isAudioDescription;

  @override
  int get hashCode => language.hashCode ^ isAudioDescription.hashCode;

  @override
  String toString() => 'AudioTrack{language: $language, '
      'isAudioDescription: $isAudioDescription}';
}

@immutable
class ConnectedFile {
  final int? id;
  final int? gid;
  final String? hash;
  final String? title;
  final int? channel;
  final int? format;
  final String? extension;
  final String? fileFormat;
  final String? mimeType;
  final int? fileSize;
  final String? url;

  const ConnectedFile({
    required this.id,
    required this.gid,
    required this.hash,
    required this.title,
    required this.channel,
    required this.format,
    required this.extension,
    required this.fileFormat,
    required this.mimeType,
    required this.fileSize,
    required this.url,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is ConnectedFile &&
          runtimeType == other.runtimeType &&
          id == other.id &&
          gid == other.gid &&
          hash == other.hash &&
          title == other.title &&
          channel == other.channel &&
          format == other.format &&
          extension == other.extension &&
          fileFormat == other.fileFormat &&
          mimeType == other.mimeType &&
          fileSize == other.fileSize &&
          url == other.url;

  @override
  int get hashCode =>
      id.hashCode ^
      gid.hashCode ^
      hash.hashCode ^
      title.hashCode ^
      channel.hashCode ^
      format.hashCode ^
      extension.hashCode ^
      fileFormat.hashCode ^
      mimeType.hashCode ^
      fileSize.hashCode ^
      url.hashCode;

  @override
  String toString() => 'ConnectedFile{id: $id, gid: $gid, '
      'hash: $hash, title: $title, channel: $channel, channel: $channel, '
      'channel: $channel, format: $format, extension: $extension, '
      'fileFormat: $fileFormat, mimeType: $mimeType, fileSize: $fileSize, '
      'url: $url}';
}

/// Structure, descriping a media entity.
@immutable
class MediaData {
  final String? remoteReference;
  final int id;
  final int gid;
  final String? hash;
  final String? title;
  final String? subtitle;
  final int channel;
  final int uploaded;
  final int created;
  final String? orderHint;
  final int studio;
  final String? thumb;
  final String? streamtype;
  final int licenseBy;
  final int originalDomain;
  final String? persons;
  final int format;
  final Map<String, Object>? customAttributes;
  final int episodeOfSeries;
  final bool isRemoteMedia;
  final bool isUGC;
  final bool isReLive;

  const MediaData({
    required this.remoteReference,
    required this.id,
    required this.gid,
    required this.hash,
    required this.title,
    required this.subtitle,
    required this.channel,
    required this.uploaded,
    required this.created,
    required this.orderHint,
    required this.studio,
    required this.thumb,
    required this.streamtype,
    required this.licenseBy,
    required this.originalDomain,
    required this.persons,
    required this.format,
    required this.customAttributes,
    required this.episodeOfSeries,
    required this.isRemoteMedia,
    required this.isUGC,
    required this.isReLive,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is MediaData &&
          runtimeType == other.runtimeType &&
          remoteReference == other.remoteReference &&
          id == other.id &&
          gid == other.gid &&
          hash == other.hash &&
          title == other.title &&
          subtitle == other.subtitle &&
          channel == other.channel &&
          uploaded == other.uploaded &&
          created == other.created &&
          orderHint == other.orderHint &&
          studio == other.studio &&
          thumb == other.thumb &&
          streamtype == other.streamtype &&
          licenseBy == other.licenseBy &&
          originalDomain == other.originalDomain &&
          persons == other.persons &&
          format == other.format &&
          customAttributes == other.customAttributes &&
          episodeOfSeries == other.episodeOfSeries &&
          isRemoteMedia == other.isRemoteMedia &&
          isUGC == other.isUGC &&
          isReLive == other.isReLive;

  @override
  int get hashCode =>
      remoteReference.hashCode ^
      id.hashCode ^
      gid.hashCode ^
      hash.hashCode ^
      title.hashCode ^
      subtitle.hashCode ^
      channel.hashCode ^
      uploaded.hashCode ^
      created.hashCode ^
      orderHint.hashCode ^
      studio.hashCode ^
      thumb.hashCode ^
      streamtype.hashCode ^
      licenseBy.hashCode ^
      originalDomain.hashCode ^
      persons.hashCode ^
      format.hashCode ^
      customAttributes.hashCode ^
      episodeOfSeries.hashCode ^
      isRemoteMedia.hashCode ^
      isUGC.hashCode ^
      isReLive.hashCode;

  @override
  String toString() => 'MediaData{remoteReference: $remoteReference, id: $id, '
      'gid: $gid, hash: $hash, title: $title, subtitle: $subtitle, '
      'channel: $channel, uploaded: $uploaded, created: $created, '
      'orderHint: $orderHint, studio: $studio, thumb: $thumb, '
      'streamtype: $streamtype, licenseBy: $licenseBy, '
      'originalDomain: $originalDomain, persons: $persons, format: $format, '
      'customAttributes: $customAttributes, episodeOfSeries: $episodeOfSeries, '
      'isRemoteMedia: $isRemoteMedia, isUGC: $isUGC, isReLive: $isReLive}';
}

/// Structure, descriping a media entity parent.
@immutable
class MediaParentData {
  final int id;
  final int gid;
  final String? hash;
  final String? title;
  final String? subtitle;
  final int created;
  final String? orderHint;
  final String? thumb;
  final String? streamtype;
  final int originalDomain;

  const MediaParentData({
    required this.id,
    required this.gid,
    required this.hash,
    required this.title,
    required this.subtitle,
    required this.created,
    required this.orderHint,
    required this.thumb,
    required this.streamtype,
    required this.originalDomain,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is MediaData &&
          runtimeType == other.runtimeType &&
          id == other.id &&
          gid == other.gid &&
          hash == other.hash &&
          title == other.title &&
          subtitle == other.subtitle &&
          created == other.created &&
          orderHint == other.orderHint &&
          thumb == other.thumb &&
          streamtype == other.streamtype &&
          originalDomain == other.originalDomain;

  @override
  int get hashCode =>
      id.hashCode ^
      gid.hashCode ^
      hash.hashCode ^
      title.hashCode ^
      subtitle.hashCode ^
      created.hashCode ^
      orderHint.hashCode ^
      thumb.hashCode ^
      streamtype.hashCode ^
      originalDomain.hashCode;

  @override
  String toString() => 'MediaData{id: $id, gid: $gid, hash: $hash, '
      'title: $title, subtitle: $subtitle, created: $created, '
      'orderHint: $orderHint, thumb: $thumb, streamtype: $streamtype, '
      'originalDomain: $originalDomain}';
}

/// Structure, descriping media properties for inheritors.
@immutable
class MediaGeneral {
  final int created;
  final String? hash;
  final int id;
  final String? streamType;
  final int gid;
  final int isPicked;
  final int isUGC;
  final int isPay;
  final int episode;
  final int season;
  final String? language;
  final int channel;
  final int licenseBy;
  final int releaseDate;
  final String? orderHint;
  final String? type;
  final String? runtime;
  final String? subtitle;
  final String? title;
  final String? teaser;
  final String? description;
  final String? purpose;
  final String? slug;
  final String? format;
  final String? contentModerationAspects;
  final int formatRaw;
  final int fileVersion;
  final int occurance;
  final int uploaded;
  final String? videoType;
  final String? podcastURL;

  const MediaGeneral({
    required this.created,
    required this.hash,
    required this.id,
    required this.streamType,
    required this.gid,
    required this.isPicked,
    required this.isUGC,
    required this.isPay,
    required this.episode,
    required this.season,
    required this.language,
    required this.channel,
    required this.licenseBy,
    required this.releaseDate,
    required this.orderHint,
    required this.type,
    required this.runtime,
    required this.subtitle,
    required this.title,
    required this.teaser,
    required this.description,
    required this.purpose,
    required this.slug,
    required this.format,
    required this.contentModerationAspects,
    required this.formatRaw,
    required this.fileVersion,
    required this.occurance,
    required this.uploaded,
    required this.videoType,
    required this.podcastURL,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is MediaGeneral &&
          runtimeType == other.runtimeType &&
          created == other.created &&
          hash == other.hash &&
          id == other.id &&
          streamType == other.streamType &&
          gid == other.gid &&
          isPicked == other.isPicked &&
          isUGC == other.isUGC &&
          isPay == other.isPay &&
          episode == other.episode &&
          season == other.season &&
          language == other.language &&
          channel == other.channel &&
          licenseBy == other.licenseBy &&
          releaseDate == other.releaseDate &&
          orderHint == other.orderHint &&
          type == other.type &&
          runtime == other.runtime &&
          subtitle == other.subtitle &&
          title == other.title &&
          teaser == other.teaser &&
          description == other.description &&
          purpose == other.purpose &&
          slug == other.slug &&
          format == other.format &&
          contentModerationAspects == other.contentModerationAspects &&
          formatRaw == other.formatRaw &&
          fileVersion == other.fileVersion &&
          occurance == other.occurance &&
          uploaded == other.uploaded &&
          videoType == other.videoType &&
          podcastURL == other.podcastURL;

  @override
  int get hashCode =>
      created.hashCode ^
      hash.hashCode ^
      id.hashCode ^
      streamType.hashCode ^
      gid.hashCode ^
      isPicked.hashCode ^
      isUGC.hashCode ^
      isPay.hashCode ^
      episode.hashCode ^
      season.hashCode ^
      language.hashCode ^
      channel.hashCode ^
      licenseBy.hashCode ^
      releaseDate.hashCode ^
      orderHint.hashCode ^
      type.hashCode ^
      runtime.hashCode ^
      subtitle.hashCode ^
      title.hashCode ^
      teaser.hashCode ^
      description.hashCode ^
      purpose.hashCode ^
      slug.hashCode ^
      format.hashCode ^
      contentModerationAspects.hashCode ^
      formatRaw.hashCode ^
      fileVersion.hashCode ^
      occurance.hashCode ^
      uploaded.hashCode ^
      videoType.hashCode ^
      podcastURL.hashCode;

  @override
  String toString() => 'MediaGeneral{created: $created, hash: $hash, id: $id, '
      'streamtype: $streamType, gid: $gid, isPicked: $isPicked, isUGC: $isUGC, '
      'isPay: $isPay, episode: $episode, season: $season, language: $language, '
      'channel: $channel, licenseby: $licenseBy, releasedate: $releaseDate, '
      'orderhint: $orderHint, type: $type, runtime: $runtime, '
      'subtitle: $subtitle, title: $title, teaser: $teaser, '
      'description: $description, purpose: $purpose, slug: $slug, '
      'format: $format, contentModerationAspects: $contentModerationAspects, '
      'formatRaw: $formatRaw, fileversion: $fileVersion, occurance: $occurance,'
      ' uploaded: $uploaded, videotype: $videoType, podcastURL: $podcastURL}';
}

/// Structure, descriping an instance of offline media.
@immutable
class OfflineMediaResult extends MediaGeneral {
  final String? downloadState;
  final String? localCover;
  final String? offlineReference;
  final int operationId;

  const OfflineMediaResult({
    required this.downloadState,
    required this.localCover,
    required this.offlineReference,
    required this.operationId,
    required int created,
    required String? hash,
    required int id,
    required String? streamType,
    required int gid,
    required int isPicked,
    required int isUGC,
    required int isPay,
    required int episode,
    required int season,
    required String? language,
    required int channel,
    required int licenseBy,
    required int releaseDate,
    required String? orderHint,
    required String? type,
    required String? runtime,
    required String? subtitle,
    required String? title,
    required String? teaser,
    required String? description,
    required String? purpose,
    required String? slug,
    required String? format,
    required String? contentModerationAspects,
    required int formatRaw,
    required int fileVersion,
    required int occurance,
    required int uploaded,
    required String? videoType,
    required String? podcastURL,
  }) : super(
          created: created,
          hash: hash,
          id: id,
          streamType: streamType,
          gid: gid,
          isPicked: isPicked,
          isUGC: isUGC,
          isPay: isPay,
          episode: episode,
          season: season,
          language: language,
          channel: channel,
          licenseBy: licenseBy,
          releaseDate: releaseDate,
          orderHint: orderHint,
          type: type,
          runtime: runtime,
          subtitle: subtitle,
          title: title,
          teaser: teaser,
          description: description,
          purpose: purpose,
          slug: slug,
          format: format,
          contentModerationAspects: contentModerationAspects,
          formatRaw: formatRaw,
          fileVersion: fileVersion,
          occurance: occurance,
          uploaded: uploaded,
          videoType: videoType,
          podcastURL: podcastURL,
        );

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      super == other &&
          other is OfflineMediaResult &&
          runtimeType == other.runtimeType &&
          downloadState == other.downloadState &&
          localCover == other.localCover &&
          offlineReference == other.offlineReference &&
          operationId == other.operationId;

  @override
  int get hashCode =>
      super.hashCode ^
      downloadState.hashCode ^
      localCover.hashCode ^
      offlineReference.hashCode ^
      operationId.hashCode;

  @override
  String toString() => 'OfflineMediaResult{downloadState: $downloadState, '
      'localCover: $localCover, offlineReference: $offlineReference, '
      'operationId: $operationId, `super`: ${super.toString()}}';
}

/// Structure, descriping nexxPLAY's playback state.
@immutable
class PlaybackState {
  final String? audioLanguage;
  final String? mediaSession;
  final double elapsedTime;
  final double currentTime;
  final int abTestVersion;
  final double duration;
  final String? playReason;
  final String? liveStatus;
  final bool isAutoPlay;
  final bool isPlayingAd;
  final bool isPlayingBumper;
  final bool isMuted;
  final bool isLocalMedia;
  final bool isInPiP;
  final bool isCasting;
  final bool canBeCommented;
  final bool isInFullscreen;
  final bool isInStoryMode;
  final bool isStitched;
  final bool isPresentationMode;
  final bool isStoryMode;
  final bool isSceneSplitMode;
  final bool isLightsOut;
  final bool isInPopOut;

  const PlaybackState({
    required this.audioLanguage,
    required this.mediaSession,
    required this.elapsedTime,
    required this.currentTime,
    required this.abTestVersion,
    required this.duration,
    required this.playReason,
    required this.liveStatus,
    required this.isAutoPlay,
    required this.isPlayingAd,
    required this.isPlayingBumper,
    required this.isMuted,
    required this.isLocalMedia,
    required this.isInPiP,
    required this.isCasting,
    required this.canBeCommented,
    required this.isInFullscreen,
    required this.isInStoryMode,
    required this.isStitched,
    required this.isPresentationMode,
    required this.isStoryMode,
    required this.isSceneSplitMode,
    required this.isLightsOut,
    required this.isInPopOut,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is PlaybackState &&
          runtimeType == other.runtimeType &&
          audioLanguage == other.audioLanguage &&
          mediaSession == other.mediaSession &&
          elapsedTime == other.elapsedTime &&
          currentTime == other.currentTime &&
          abTestVersion == other.abTestVersion &&
          duration == other.duration &&
          playReason == other.playReason &&
          liveStatus == other.liveStatus &&
          isAutoPlay == other.isAutoPlay &&
          isPlayingAd == other.isPlayingAd &&
          isPlayingBumper == other.isPlayingBumper &&
          isMuted == other.isMuted &&
          isLocalMedia == other.isLocalMedia &&
          isInPiP == other.isInPiP &&
          isCasting == other.isCasting &&
          canBeCommented == other.canBeCommented &&
          isInFullscreen == other.isInFullscreen &&
          isInStoryMode == other.isInStoryMode &&
          isStitched == other.isStitched &&
          isPresentationMode == other.isPresentationMode &&
          isStoryMode == other.isStoryMode &&
          isSceneSplitMode == other.isSceneSplitMode &&
          isLightsOut == other.isLightsOut &&
          isInPopOut == other.isInPopOut;

  @override
  int get hashCode =>
      audioLanguage.hashCode ^
      mediaSession.hashCode ^
      elapsedTime.hashCode ^
      currentTime.hashCode ^
      abTestVersion.hashCode ^
      duration.hashCode ^
      playReason.hashCode ^
      liveStatus.hashCode ^
      isAutoPlay.hashCode ^
      isPlayingAd.hashCode ^
      isPlayingBumper.hashCode ^
      isMuted.hashCode ^
      isLocalMedia.hashCode ^
      isInPiP.hashCode ^
      isCasting.hashCode ^
      canBeCommented.hashCode ^
      isInFullscreen.hashCode ^
      isInStoryMode.hashCode ^
      isStitched.hashCode ^
      isPresentationMode.hashCode ^
      isStoryMode.hashCode ^
      isSceneSplitMode.hashCode ^
      isLightsOut.hashCode ^
      isInPopOut.hashCode;

  @override
  String toString() => 'PlaybackState{audioLanguage: $audioLanguage, '
      'mediaSession: $mediaSession, '
      'elapsedTime: $elapsedTime, currentTime: $currentTime, '
      'abTestVersion: $abTestVersion, duration: $duration, '
      'playReason: $playReason, liveStatus: $liveStatus, '
      'isAutoPlay: $isAutoPlay, isPlayingAd: $isPlayingAd, '
      'isPlayingBumper: $isPlayingBumper, isMuted: $isMuted, '
      'isLocalMedia: $isLocalMedia, isInPiP: $isInPiP, isCasting: $isCasting, '
      'canBeCommented: $canBeCommented, isInFullscreen: $isInFullscreen, '
      'isInStoryMode: $isInStoryMode, isStitched: $isStitched, '
      'isPresentationMode: $isPresentationMode, isStoryMode: $isStoryMode, '
      'isSceneSplitMode: $isSceneSplitMode, isLightsOut: $isLightsOut, '
      'isInPopOut: $isInPopOut}';
}
