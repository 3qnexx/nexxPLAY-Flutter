package tv.nexx.flutter.android.platform_view;

import tv.nexx.android.play.NexxPLAYNotification;
import tv.nexx.android.play.player.Player;

public interface NoOpNexxPlayListener extends NexxPLAYNotification.Listener {
    @Override
    default void onPlayerStateChanged(boolean playWhenReady, Player.State current) {
    }

    @Override
    default void onVideoSizeChanged(int width, int height, float pixelWidthAspectRatio) {
    }

    @Override
    default void onPlayerError(String reason, String details) {
    }

    @Override
    default void onFullScreen(boolean fullScreen) {
    }

    @Override
    default void onPlayerEvent(NexxPLAYNotification.IPlayerEvent e) {
    }
}
