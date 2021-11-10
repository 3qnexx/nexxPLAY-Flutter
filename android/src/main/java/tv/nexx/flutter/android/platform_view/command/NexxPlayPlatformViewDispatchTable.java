package tv.nexx.flutter.android.platform_view.command;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTable;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

public class NexxPlayPlatformViewDispatchTable {

    private static final NexxPlayPlatformViewDispatchTable INSTANCE = new NexxPlayPlatformViewDispatchTable();
    private static final DispatchTable<String, NexxPlayPlatformView, NexxPlayDispatchPayload> DISPATCH_TABLE = DispatchTable.threadConfined();

    static {
        DISPATCH_TABLE.set("start", StartPlayerCommand.create());
        DISPATCH_TABLE.set("clearCache", ClearCacheCommand.create());
        DISPATCH_TABLE.set("updateConfiguration", UpdateConfigurationCommand.create());
        DISPATCH_TABLE.set("updateEnvironment", UpdateEnvironmentCommand.create());
        DISPATCH_TABLE.set("play", PlayCommand.create());
        DISPATCH_TABLE.set("pause", PauseCommand.create());
        DISPATCH_TABLE.set("toggle", ToggleCommand.create());
        DISPATCH_TABLE.set("mute", MuteCommand.create());
        DISPATCH_TABLE.set("unmute", UnmuteCommand.create());
        DISPATCH_TABLE.set("next", NextCommand.create());
        DISPATCH_TABLE.set("previous", PreviousCommand.create());
        DISPATCH_TABLE.set("seekTo", SeekToCommand.create());
        DISPATCH_TABLE.set("seekBy", SeekByCommand.create());
        DISPATCH_TABLE.set("swapToPosition", SwapToPositionCommand.create());
        DISPATCH_TABLE.set("swapToMediaItem", SwapToMediaItemCommand.create());
        DISPATCH_TABLE.set("swapToGlobalID", SwapToGlobalIDCommand.create());
        DISPATCH_TABLE.set("swapToRemoteMedia", SwapToRemoteMediaCommand.create());
        DISPATCH_TABLE.set("getCaptionData", GetCaptionDataCommand.create());
        DISPATCH_TABLE.set("getMediaData", GetMediaDataCommand.create());
        DISPATCH_TABLE.set("getCaptionLanguages", GetCaptionLanguagesCommand.create());
        DISPATCH_TABLE.set("getAudioLanguages", GetAudioLanguagesCommand.create());
        DISPATCH_TABLE.set("getCurrentTime", GetCurrentTimeCommand.create());
        DISPATCH_TABLE.set("isPlaying", IsPlayingCommand.create());
        DISPATCH_TABLE.set("isPlayingAd", IsPlayingAdCommand.create());
        DISPATCH_TABLE.set("isMuted", IsMutedCommand.create());
        DISPATCH_TABLE.set("isInPiP", IsInPIPCommand.create());
    }

    private NexxPlayPlatformViewDispatchTable() {
    }

    public static NexxPlayPlatformViewDispatchTable get() {
        return INSTANCE;
    }

    public void dispatch(String name, NexxPlayPlatformView view, NexxPlayDispatchPayload args) {
        DISPATCH_TABLE.dispatch(name, view, args);
    }

}
