package tv.nexx.flutter.android.platform_view;

import tv.nexx.android.play.INexxPLAY;
import tv.nexx.android.play.MediaSourceType;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTable;
import tv.nexx.flutter.android.nexx_player.NexxPlayerConfiguration;

class NexxPlayerStateDispatchTable {

    private static final DispatchTable<MediaSourceType, INexxPLAY, NexxPlayerConfiguration> DISPATCH_TABLE = DispatchTable.threadConfined();

    static {
        DISPATCH_TABLE.set(MediaSourceType.NORMAL, (player, config) ->
                player.startPlay(config.getPlayMode(), config.getMediaID(), config.nexxPLAYConfiguration()));
        DISPATCH_TABLE.set(MediaSourceType.REMOTE, (player, config) ->
                player.startPlayWithRemoteMedia(config.getPlayMode(), config.getMediaID(), config.getProvider(), config.nexxPLAYConfiguration()));
        DISPATCH_TABLE.set(MediaSourceType.GLOBAL, (player, config) ->
                player.startPlayWithGlobalID(config.getMediaID(), config.nexxPLAYConfiguration()));
    }

    private NexxPlayerStateDispatchTable() {
    }

    // Thread-safe initialization via classloader
    private static class InstanceHolder {
        private static final NexxPlayerStateDispatchTable INSTANCE =
                new NexxPlayerStateDispatchTable();
    }

    static NexxPlayerStateDispatchTable get() {
        return NexxPlayerStateDispatchTable.InstanceHolder.INSTANCE;
    }

    public void dispatch(MediaSourceType type, INexxPLAY player, NexxPlayerConfiguration conf) {
        DISPATCH_TABLE.dispatch(type, player, conf);
    }

}
