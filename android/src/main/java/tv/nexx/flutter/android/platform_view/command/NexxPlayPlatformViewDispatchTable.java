package tv.nexx.flutter.android.platform_view.command;

import java.util.Map;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTable;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

public class NexxPlayPlatformViewDispatchTable {

    private final DispatchTable<String, NexxPlayPlatformView, NexxPlayDispatchPayload> table = DispatchTable.threadConfined();

    private NexxPlayPlatformViewDispatchTable(Map<String, Object> configuration) {
        table.set("start", StartPlayerCommand.create(configuration));
        table.set("clearCache", ClearCacheCommand.create());
        table.set("updateConfiguration", UpdateConfigurationCommand.create());
        table.set("updateEnvironment", UpdateEnvironmentCommand.create());
        table.set("play", PlayCommand.create());
        table.set("pause", PauseCommand.create());
        table.set("toggle", ToggleCommand.create());
        table.set("mute", MuteCommand.create());
        table.set("unmute", UnmuteCommand.create());
        table.set("next", NextCommand.create());
        table.set("previous", PreviousCommand.create());
        table.set("seekTo", SeekToCommand.create());
        table.set("seekBy", SeekByCommand.create());
        table.set("swapToPosition", SwapToPositionCommand.create());
        table.set("swapToMediaItem", SwapToMediaItemCommand.create());
        table.set("swapToGlobalID", SwapToGlobalIDCommand.create());
        table.set("swapToRemoteMedia", SwapToRemoteMediaCommand.create());
        table.set("getAudioTracks", GetAudioTracksCommand.create());
        table.set("getCurrentMedia", GetCurrentMediaCommand.create());
        table.set("getCurrentMediaParent", GetCurrentMediaParentCommand.create());
        table.set("getCurrentPlaybackState", GetCurrentPlaybackStateCommand.create());
        table.set("getCurrentTime", GetCurrentTimeCommand.create());
        table.set("getConnectedFiles", GetConnectedFilesCommand.create());
        table.set("isPlaying", IsPlayingCommand.create());
        table.set("isPlayingAd", IsPlayingAdCommand.create());
        table.set("isMuted", IsMutedCommand.create());
        table.set("isInPiP", IsInPIPCommand.create());
        table.set("isCasting", IsCastingCommand.create());
        table.set("startDownloadingLocalMedia", StartDownloadingLocalMediaCommand.create());
        table.set("listLocalMedia", ListLocalMediaCommand.create());
        table.set("hasDownloadOfLocalMedia", HasDownloadOfLocalMediaCommand.create());
        table.set("clearLocalMedia", ClearLocalMediaCommand.create());
        table.set("diskSpaceUsedForLocalMedia", DiskSpaceUsedForLocalMediaCommand.create());
    }

    public static NexxPlayPlatformViewDispatchTable create(Map<String, Object> configuration) {
        return new NexxPlayPlatformViewDispatchTable(configuration);
    }

    public void dispatch(String name, NexxPlayPlatformView view, NexxPlayDispatchPayload args) {
        table.dispatch(name, view, args);
    }

}
