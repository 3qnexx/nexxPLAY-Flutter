package tv.nexx.flutter.android.platform_view;

import android.widget.FrameLayout;
import android.widget.RelativeLayout;

class NexxPlayerViewHost {

    private final RelativeLayout root;
    private final FrameLayout playerArea;

    NexxPlayerViewHost(RelativeLayout root, FrameLayout playerArea) {
        this.root = root;
        this.playerArea = playerArea;
    }

    public RelativeLayout getRoot() {
        return root;
    }

    public FrameLayout getPlayerArea() {
        return playerArea;
    }
}
