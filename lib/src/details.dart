import 'package:flutter/foundation.dart';

/// Description of an audio track.
@immutable
class AudioTrack {
  final String? language;
  final String? role;

  const AudioTrack({
    required this.language,
    required this.role,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is AudioTrack &&
          runtimeType == other.runtimeType &&
          language == other.language &&
          role == other.role;

  @override
  int get hashCode => language.hashCode ^ role.hashCode;

  @override
  String toString() => 'AudioTrack{language: $language, role: $role}';
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
  final int studio;
  final String? thumb;
  final String? streamtype;
  final int licenseBy;
  final int originalDomain;
  final int format;
  final Map<dynamic, dynamic>? customAttributes;
  final bool isUGC;
  final int releaseDate;
  final String? remoteProvider;
  final bool isForKids;
  final bool isPanorama;
  final bool isHDR;
  final bool isPay;
  final bool isPicked;

  const MediaData({
    required this.remoteReference,
    required this.id,
    required this.gid,
    required this.hash,
    required this.title,
    required this.subtitle,
    required this.channel,
    required this.studio,
    required this.thumb,
    required this.streamtype,
    required this.licenseBy,
    required this.originalDomain,
    required this.format,
    required this.isUGC,
    required this.releaseDate,
    required this.remoteProvider,
    required this.isForKids,
    required this.isPanorama,
    required this.isHDR,
    required this.isPay,
    required this.isPicked,
    required this.customAttributes,
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
          studio == other.studio &&
          thumb == other.thumb &&
          streamtype == other.streamtype &&
          licenseBy == other.licenseBy &&
          originalDomain == other.originalDomain &&
          format == other.format &&
          customAttributes == other.customAttributes &&
          isUGC == other.isUGC &&
          releaseDate == other.releaseDate &&
          remoteProvider == other.remoteProvider &&
          isForKids == other.isForKids &&
          isPanorama == other.isPanorama &&
          isHDR == other.isHDR &&
          isPay == other.isPay &&
          isPicked == other.isPicked;

  @override
  int get hashCode =>
      remoteReference.hashCode ^
      id.hashCode ^
      gid.hashCode ^
      hash.hashCode ^
      title.hashCode ^
      subtitle.hashCode ^
      channel.hashCode ^
      studio.hashCode ^
      thumb.hashCode ^
      streamtype.hashCode ^
      licenseBy.hashCode ^
      originalDomain.hashCode ^
      format.hashCode ^
      customAttributes.hashCode ^
      isUGC.hashCode ^
      releaseDate.hashCode ^
      remoteProvider.hashCode ^
      isForKids.hashCode ^
      isPanorama.hashCode ^
      isHDR.hashCode ^
      isPay.hashCode ^
      isPicked.hashCode;

  @override
  String toString() => 'MediaData{remoteReference: $remoteReference, id: $id, '
      'gid: $gid, hash: $hash, title: $title, subtitle: $subtitle, '
      'channel: $channel, studio: $studio, thumb: $thumb, '
      'streamtype: $streamtype, licenseBy: $licenseBy, '
      'originalDomain: $originalDomain, format: $format, '
      'customAttributes: $customAttributes, isUGC: $isUGC, '
      'releaseDate: $releaseDate, remoteProvider: $remoteProvider, '
      'isForKids: $isForKids, isPanorama: $isPanorama, isHDR: $isHDR, '
      'isPay: $isPay, isPicked: $isPicked}';
}

/// Structure, descriping a media entity parent.
@immutable
class MediaParentData {
  final int id;
  final int gid;
  final String? hash;
  final String? title;
  final String? subtitle;
  final String? thumb;
  final String? streamtype;
  final int originalDomain;
  final int releaseDate;
  final int licenseBy;
  final int channel;
  final int format;
  final Map<dynamic, dynamic>? customAttributes;

  const MediaParentData({
    required this.id,
    required this.gid,
    required this.hash,
    required this.title,
    required this.subtitle,
    required this.thumb,
    required this.streamtype,
    required this.originalDomain,
    required this.releaseDate,
    required this.licenseBy,
    required this.channel,
    required this.format,
    required this.customAttributes,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is MediaParentData &&
          runtimeType == other.runtimeType &&
          id == other.id &&
          gid == other.gid &&
          hash == other.hash &&
          title == other.title &&
          subtitle == other.subtitle &&
          thumb == other.thumb &&
          streamtype == other.streamtype &&
          originalDomain == other.originalDomain &&
          releaseDate == other.releaseDate &&
          licenseBy == other.licenseBy &&
          channel == other.channel &&
          format == other.format &&
          customAttributes == other.customAttributes;

  @override
  int get hashCode =>
      id.hashCode ^
      gid.hashCode ^
      hash.hashCode ^
      title.hashCode ^
      subtitle.hashCode ^
      thumb.hashCode ^
      streamtype.hashCode ^
      originalDomain.hashCode ^
      releaseDate.hashCode ^
      licenseBy.hashCode ^
      channel.hashCode ^
      format.hashCode ^
      customAttributes.hashCode;

  @override
  String toString() => 'MediaData{id: $id, gid: $gid, hash: $hash, '
      'title: $title, subtitle: $subtitle, thumb: $thumb, '
      'streamtype: $streamtype, originalDomain: $originalDomain, '
      'releaseDate: $releaseDate, licenseBy: $licenseBy, channel: $channel, '
      'format: $format, customAttributes: $customAttributes}';
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
  final int forKids;
  final String? videoType;
  final String? podcastURL;
  final String? containerPurpose;
  final String? audioType;

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
    required this.forKids,
    required this.videoType,
    required this.podcastURL,
    required this.containerPurpose,
    required this.audioType,
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
          forKids == other.forKids &&
          videoType == other.videoType &&
          podcastURL == other.podcastURL &&
          containerPurpose == other.containerPurpose &&
          audioType == other.audioType;

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
      forKids.hashCode ^
      videoType.hashCode ^
      podcastURL.hashCode ^
      containerPurpose.hashCode ^
      audioType.hashCode;

  @override
  String toString() => 'MediaGeneral{created: $created, hash: $hash, id: $id, '
      'streamtype: $streamType, gid: $gid, isPicked: $isPicked, isUGC: $isUGC, '
      'isPay: $isPay, episode: $episode, season: $season, language: $language, '
      'channel: $channel, licenseby: $licenseBy, releasedate: $releaseDate, '
      'type: $type, runtime: $runtime, subtitle: $subtitle, title: $title, '
      'teaser: $teaser, description: $description, purpose: $purpose, '
      'slug: $slug, format: $format, forKids: $forKids'
      'contentModerationAspects: $contentModerationAspects, '
      'formatRaw: $formatRaw, fileversion: $fileVersion, occurance: $occurance,'
      ' uploaded: $uploaded, videotype: $videoType, podcastURL: $podcastURL, '
      'containerPurpose: $containerPurpose, audioType: $audioType}';
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
    required int forKids,
    required String? containerPurpose,
    required String? audioType,
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
          forKids: forKids,
          containerPurpose: containerPurpose,
          audioType: audioType,
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
class CurrentPlaybackState {
  final String? audioLanguage;
  final String? mediaSession;
  final double elapsedTime;
  final double currentTime;
  final int abTestVersion;
  final double duration;
  final String? playReason;
  final bool isAutoPlay;
  final bool isPlayingAd;
  final bool isMuted;
  final bool isLocalMedia;
  final bool isInPiP;
  final bool isCasting;
  final bool canBeCommented;
  final bool isInFullscreen;
  final bool isStitched;
  final bool isInPopOut;
  final String? textTrackLanguage;
  final String? playbackMode;
  final String? protection;
  final String? steamingFilter;
  final String? protocol;
  final String? codec;
  final bool isPlaying;
  final bool isHDR;
  final bool isWaitingForPremiere;
  final bool isReLive;
  final String? playbackState;

  const CurrentPlaybackState({
    required this.audioLanguage,
    required this.mediaSession,
    required this.elapsedTime,
    required this.currentTime,
    required this.abTestVersion,
    required this.duration,
    required this.playReason,
    required this.isAutoPlay,
    required this.isPlayingAd,
    required this.isMuted,
    required this.isLocalMedia,
    required this.isInPiP,
    required this.isCasting,
    required this.canBeCommented,
    required this.isInFullscreen,
    required this.isStitched,
    required this.isInPopOut,
    required this.textTrackLanguage,
    required this.playbackMode,
    required this.protection,
    required this.steamingFilter,
    required this.protocol,
    required this.codec,
    required this.isPlaying,
    required this.isHDR,
    required this.isWaitingForPremiere,
    required this.isReLive,
    required this.playbackState,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is CurrentPlaybackState &&
          runtimeType == other.runtimeType &&
          audioLanguage == other.audioLanguage &&
          mediaSession == other.mediaSession &&
          elapsedTime == other.elapsedTime &&
          currentTime == other.currentTime &&
          abTestVersion == other.abTestVersion &&
          duration == other.duration &&
          playReason == other.playReason &&
          isAutoPlay == other.isAutoPlay &&
          isPlayingAd == other.isPlayingAd &&
          isMuted == other.isMuted &&
          isLocalMedia == other.isLocalMedia &&
          isInPiP == other.isInPiP &&
          isCasting == other.isCasting &&
          canBeCommented == other.canBeCommented &&
          isInFullscreen == other.isInFullscreen &&
          isStitched == other.isStitched &&
          isInPopOut == other.isInPopOut &&
          textTrackLanguage == other.textTrackLanguage &&
          playbackMode == other.playbackMode &&
          protection == other.protection &&
          steamingFilter == other.steamingFilter &&
          protocol == other.protocol &&
          codec == other.codec &&
          isPlaying == other.isPlaying &&
          isHDR == other.isHDR &&
          isWaitingForPremiere == other.isWaitingForPremiere &&
          isReLive == other.isReLive &&
          playbackState == other.playbackState;

  @override
  int get hashCode =>
      audioLanguage.hashCode ^
      mediaSession.hashCode ^
      elapsedTime.hashCode ^
      currentTime.hashCode ^
      abTestVersion.hashCode ^
      duration.hashCode ^
      playReason.hashCode ^
      isAutoPlay.hashCode ^
      isPlayingAd.hashCode ^
      isMuted.hashCode ^
      isLocalMedia.hashCode ^
      isInPiP.hashCode ^
      isCasting.hashCode ^
      canBeCommented.hashCode ^
      isInFullscreen.hashCode ^
      isStitched.hashCode ^
      isInPopOut.hashCode ^
      textTrackLanguage.hashCode ^
      playbackMode.hashCode ^
      protection.hashCode ^
      steamingFilter.hashCode ^
      protocol.hashCode ^
      codec.hashCode ^
      isPlaying.hashCode ^
      isHDR.hashCode ^
      isWaitingForPremiere.hashCode ^
      isReLive.hashCode ^
      playbackState.hashCode;

  @override
  String toString() => 'PlaybackState{audioLanguage: $audioLanguage, '
      'mediaSession: $mediaSession, elapsedTime: $elapsedTime, '
      'currentTime: $currentTime, abTestVersion: $abTestVersion, '
      'duration: $duration, playReason: $playReason, isAutoPlay: $isAutoPlay, '
      'isPlayingAd: $isPlayingAd, isMuted: $isMuted, '
      'isLocalMedia: $isLocalMedia, isInPiP: $isInPiP, '
      'isCasting: $isCasting, canBeCommented: $canBeCommented, '
      'isInFullscreen: $isInFullscreen, isStitched: $isStitched, '
      'isInPopOut: $isInPopOut, textTrackLanguage: $textTrackLanguage, '
      'playbackMode: $playbackMode, protection: $protection, '
      'steamingFilter: $steamingFilter, protocol: $protocol, codec: $codec, '
      'isPlaying: $isPlaying, isHDR: $isHDR, '
      'isWaitingForPremiere: $isWaitingForPremiere, isReLive: $isReLive, '
      'playbackState: $playbackState}';
}
