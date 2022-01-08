package tv.nexx.flutter.android.platform_view;

import tv.nexx.android.play.NexxPLAYNotification;
import tv.nexx.android.play.player.IPlayer;

public interface NoOpNexxPlayListener extends NexxPLAYNotification.Listener {
    @Override
    default void onPlayerStateChanged(boolean playWhenReady, IPlayer.State current) {
    }

    @Override
    default void onPlayerEvent(NexxPLAYNotification.IPlayerEvent e) {
    }

    @Override
    default void onPlayerError(String reason, String details) {
    }

    @Override
    default void onFullScreen(boolean fullScreen) {
    }
}
