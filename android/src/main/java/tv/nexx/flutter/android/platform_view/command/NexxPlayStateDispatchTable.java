package tv.nexx.flutter.android.platform_view.command;

import tv.nexx.android.play.INexxPLAY;
import tv.nexx.android.play.MediaSourceType;
import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTable;

class NexxPlayStateDispatchTable {

    private static final DispatchTable<MediaSourceType, INexxPLAY, NexxPlayPlaybackPayload> DISPATCH_TABLE = DispatchTable.threadConfined();

    static {
        DISPATCH_TABLE.set(MediaSourceType.NORMAL, (player, payload) ->
                player.startPlay(
                        payload.playback().playMode(),
                        payload.playback().mediaID(),
                        payload.configuration()
                )
        );
        DISPATCH_TABLE.set(MediaSourceType.REMOTE, (player, payload) ->
                player.startPlayWithRemoteMedia(
                        payload.playback().playMode(),
                        payload.playback().mediaID(),
                        payload.playback().provider(),
                        payload.configuration()
                )
        );
        DISPATCH_TABLE.set(MediaSourceType.GLOBAL, (player, payload) ->
                player.startPlayWithGlobalID(
                        payload.playback().mediaID(),
                        payload.configuration()
                )
        );
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

    public void dispatch(MediaSourceType type, INexxPLAY player, NexxPlayPlaybackPayload payload) {
        DISPATCH_TABLE.dispatch(type, player, payload);
    }

}
