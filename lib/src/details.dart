import 'package:flutter/foundation.dart';

@immutable
class Caption {
  final String? id;
  final String? title;
  final String? hash;
  final String? langauge;
  final String? assetRoot;
  final String? flagUrl;
  final int isAudioDescription;
  final List<CaptionData>? data;

  const Caption({
    required this.id,
    required this.title,
    required this.hash,
    required this.langauge,
    required this.assetRoot,
    required this.flagUrl,
    required this.isAudioDescription,
    required this.data,
  });

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is Caption &&
        other.id == id &&
        other.title == title &&
        other.hash == hash &&
        other.langauge == langauge &&
        other.assetRoot == assetRoot &&
        other.flagUrl == flagUrl &&
        other.isAudioDescription == isAudioDescription &&
        listEquals(other.data, data);
  }

  @override
  int get hashCode {
    return id.hashCode ^
        title.hashCode ^
        hash.hashCode ^
        langauge.hashCode ^
        assetRoot.hashCode ^
        flagUrl.hashCode ^
        isAudioDescription.hashCode ^
        data.hashCode;
  }

  @override
  String toString() {
    return 'Caption(id: $id, title: $title, hash: $hash, langauge: $langauge, '
        'assetRoot: $assetRoot, flagUrl: $flagUrl, isAudioDescription: '
        '$isAudioDescription, data: $data)';
  }
}

@immutable
class CaptionData {
  final int id;
  final String? caption;
  final int fromms;
  final int toms;

  const CaptionData({
    required this.id,
    required this.caption,
    required this.fromms,
    required this.toms,
  });

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is CaptionData &&
        other.id == id &&
        other.caption == caption &&
        other.fromms == fromms &&
        other.toms == toms;
  }

  @override
  int get hashCode {
    return id.hashCode ^ caption.hashCode ^ fromms.hashCode ^ toms.hashCode;
  }

  @override
  String toString() {
    return 'CaptionData(id: $id, caption: $caption, fromms: $fromms, '
        'toms: $toms)';
  }
}

@immutable
class MediaData {
  final String? playReason;
  final int isPlayingAd;
  final String? persons;
  final int domain;
  final String? mediaSessionParent;
  final int autoplay;
  final String? studio;
  final String? studioAdRef;
  final int mediaID;
  final String? hash;
  final String? title;
  final String? subtitle;
  final String? teaser;
  final String? description;
  final String? channel;
  final int uploaded;
  final int created;
  final String? orderHint;
  final int isPresentation;
  final String? currentCaptionLanguage;
  final String? currentAudioLanguage;
  final String? channelAdRef;
  final int channelId;
  final String? thumb;
  final String? thumbABT;
  final String? streamType;
  final String? runtime;
  final int licenseBy;
  final double currentPlaybackSpeed;
  final int isBumper;
  final int isStitched;
  final String? orientation;
  final int hasAudio;
  final int globalID;
  final int chosenAbVersion;
  final String? playbackMode;
  final int isRemoteMedia;
  final String? remoteReference;
  final int isStory;
  final int isSceneSplit;
  final double currentTime;
  final double currentDuration;
  final String? mediaSession;
  final int formatId;
  final String? format;

  const MediaData({
    required this.playReason,
    required this.isPlayingAd,
    required this.persons,
    required this.domain,
    required this.mediaSessionParent,
    required this.autoplay,
    required this.studio,
    required this.studioAdRef,
    required this.mediaID,
    required this.hash,
    required this.title,
    required this.subtitle,
    required this.teaser,
    required this.description,
    required this.channel,
    required this.uploaded,
    required this.created,
    required this.orderHint,
    required this.isPresentation,
    required this.currentCaptionLanguage,
    required this.currentAudioLanguage,
    required this.channelAdRef,
    required this.channelId,
    required this.thumb,
    required this.thumbABT,
    required this.streamType,
    required this.runtime,
    required this.licenseBy,
    required this.currentPlaybackSpeed,
    required this.isBumper,
    required this.isStitched,
    required this.orientation,
    required this.hasAudio,
    required this.globalID,
    required this.chosenAbVersion,
    required this.playbackMode,
    required this.isRemoteMedia,
    required this.remoteReference,
    required this.isStory,
    required this.isSceneSplit,
    required this.currentTime,
    required this.currentDuration,
    required this.mediaSession,
    required this.formatId,
    required this.format,
  });

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is MediaData &&
        other.playReason == playReason &&
        other.isPlayingAd == isPlayingAd &&
        other.persons == persons &&
        other.domain == domain &&
        other.mediaSessionParent == mediaSessionParent &&
        other.autoplay == autoplay &&
        other.studio == studio &&
        other.studioAdRef == studioAdRef &&
        other.mediaID == mediaID &&
        other.hash == hash &&
        other.title == title &&
        other.subtitle == subtitle &&
        other.teaser == teaser &&
        other.description == description &&
        other.channel == channel &&
        other.uploaded == uploaded &&
        other.created == created &&
        other.orderHint == orderHint &&
        other.isPresentation == isPresentation &&
        other.currentCaptionLanguage == currentCaptionLanguage &&
        other.currentAudioLanguage == currentAudioLanguage &&
        other.channelAdRef == channelAdRef &&
        other.channelId == channelId &&
        other.thumb == thumb &&
        other.thumbABT == thumbABT &&
        other.streamType == streamType &&
        other.runtime == runtime &&
        other.licenseBy == licenseBy &&
        other.currentPlaybackSpeed == currentPlaybackSpeed &&
        other.isBumper == isBumper &&
        other.isStitched == isStitched &&
        other.orientation == orientation &&
        other.hasAudio == hasAudio &&
        other.globalID == globalID &&
        other.chosenAbVersion == chosenAbVersion &&
        other.playbackMode == playbackMode &&
        other.isRemoteMedia == isRemoteMedia &&
        other.remoteReference == remoteReference &&
        other.isStory == isStory &&
        other.isSceneSplit == isSceneSplit &&
        other.currentTime == currentTime &&
        other.currentDuration == currentDuration &&
        other.mediaSession == mediaSession &&
        other.formatId == formatId &&
        other.format == format;
  }

  @override
  int get hashCode {
    return playReason.hashCode ^
        isPlayingAd.hashCode ^
        persons.hashCode ^
        domain.hashCode ^
        mediaSessionParent.hashCode ^
        autoplay.hashCode ^
        studio.hashCode ^
        studioAdRef.hashCode ^
        mediaID.hashCode ^
        hash.hashCode ^
        title.hashCode ^
        subtitle.hashCode ^
        teaser.hashCode ^
        description.hashCode ^
        channel.hashCode ^
        uploaded.hashCode ^
        created.hashCode ^
        orderHint.hashCode ^
        isPresentation.hashCode ^
        currentCaptionLanguage.hashCode ^
        currentAudioLanguage.hashCode ^
        channelAdRef.hashCode ^
        channelId.hashCode ^
        thumb.hashCode ^
        thumbABT.hashCode ^
        streamType.hashCode ^
        runtime.hashCode ^
        licenseBy.hashCode ^
        currentPlaybackSpeed.hashCode ^
        isBumper.hashCode ^
        isStitched.hashCode ^
        orientation.hashCode ^
        hasAudio.hashCode ^
        globalID.hashCode ^
        chosenAbVersion.hashCode ^
        playbackMode.hashCode ^
        isRemoteMedia.hashCode ^
        remoteReference.hashCode ^
        isStory.hashCode ^
        isSceneSplit.hashCode ^
        currentTime.hashCode ^
        currentDuration.hashCode ^
        mediaSession.hashCode ^
        formatId.hashCode ^
        format.hashCode;
  }

  @override
  String toString() {
    return 'MediaData(playReason: $playReason, isPlayingAd: $isPlayingAd, '
        'persons: $persons, domain: $domain, mediaSessionParent: '
        '$mediaSessionParent, autoplay: $autoplay, studio: $studio, '
        'studioAdRef: $studioAdRef, mediaID: $mediaID, hash: $hash, title: '
        '$title, subtitle: $subtitle, teaser: $teaser, description: '
        '$description, channel: $channel, uploaded: $uploaded, created: '
        '$created, orderHint: $orderHint, isPresentation: $isPresentation, '
        'currentCaptionLanguage: $currentCaptionLanguage, currentAudioLanguage:'
        ' $currentAudioLanguage, channelAdRef: $channelAdRef, channelId: '
        '$channelId, thumb: $thumb, thumbABT: $thumbABT, streamType: '
        '$streamType, runtime: $runtime, licenseBy: $licenseBy, '
        'currentPlaybackSpeed: $currentPlaybackSpeed, isBumper: $isBumper, '
        'isStitched: $isStitched, orientation: $orientation, hasAudio: '
        '$hasAudio, globalID: $globalID, chosenAbVersion: $chosenAbVersion, '
        'playbackMode: $playbackMode, isRemoteMedia: $isRemoteMedia, '
        'remoteReference: $remoteReference, isStory: $isStory, isSceneSplit: '
        '$isSceneSplit, currentTime: $currentTime, currentDuration: '
        '$currentDuration, mediaSession: $mediaSession, formatId: $formatId, '
        'format: $format)';
  }
}
