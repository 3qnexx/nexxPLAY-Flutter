package tv.nexx.flutter.android.platform_view;

import tv.nexx.android.play.INexxPLAY;
import tv.nexx.android.play.MediaSourceType;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTable;
import tv.nexx.flutter.android.nexxplay.NexxPlayConfiguration;

class NexxPlayStateDispatchTable {

    private static final DispatchTable<MediaSourceType, INexxPLAY, NexxPlayConfiguration> DISPATCH_TABLE = DispatchTable.threadConfined();

    static {
        DISPATCH_TABLE.set(MediaSourceType.NORMAL, (player, config) ->
                player.startPlay(config.getPlayMode(), config.getMediaID(), config.nexxPLAYConfiguration()));
        DISPATCH_TABLE.set(MediaSourceType.REMOTE, (player, config) ->
                player.startPlayWithRemoteMedia(config.getPlayMode(), config.getMediaID(), config.getProvider(), config.nexxPLAYConfiguration()));
        DISPATCH_TABLE.set(MediaSourceType.GLOBAL, (player, config) ->
                player.startPlayWithGlobalID(config.getMediaID(), config.nexxPLAYConfiguration()));
    }

    private NexxPlayStateDispatchTable() {
    }

    // Thread-safe initialization via classloader
    private static class InstanceHolder {
        private static final NexxPlayStateDispatchTable INSTANCE =
                new NexxPlayStateDispatchTable();
    }

    static NexxPlayStateDispatchTable get() {
        return NexxPlayStateDispatchTable.InstanceHolder.INSTANCE;
    }

    public void dispatch(MediaSourceType type, INexxPLAY player, NexxPlayConfiguration conf) {
        DISPATCH_TABLE.dispatch(type, player, conf);
    }

}
