package tv.nexx.flutter.android.platform_view;

import android.content.res.Configuration;

import tv.nexx.android.play.INexxPLAY;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.android.event.AndroidEventVisitor;
import tv.nexx.flutter.android.android.event.OnPIPModeChangedEvent;
import tv.nexx.flutter.android.android.event.OnUserLeaveHintEvent;
import tv.nexx.flutter.android.estd.functional.Consumer;

class AndroidEventNexxPlayAdapter implements AndroidEventVisitor, Consumer<AndroidEvent> {

    private final INexxPLAY player;

    private AndroidEventNexxPlayAdapter(INexxPLAY player) {
        this.player = player;
    }

    public static AndroidEventNexxPlayAdapter of(INexxPLAY player) {
        return new AndroidEventNexxPlayAdapter(player);
    }

    @Override
    public void visit(OnUserLeaveHintEvent event) {
        player.onUserLeaveHint();
    }

    @Override
    public void visit(OnPIPModeChangedEvent event) {
        final boolean isPIP = event.isInPictureInPictureMode();
        final Configuration config = event.getNewConfig();
        player.onPictureInPictureModeChanged(isPIP, config);
    }

    @Override
    public void accept(AndroidEvent value) {
        value.visit(this);
    }
}
