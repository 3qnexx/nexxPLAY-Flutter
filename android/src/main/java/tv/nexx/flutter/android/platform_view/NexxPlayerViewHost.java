package tv.nexx.flutter.android.platform_view;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.content.Context;
import android.graphics.Color;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class NexxPlayerViewHost {

    private final RelativeLayout root;
    private final FrameLayout playerArea;

    private NexxPlayerViewHost(RelativeLayout root, FrameLayout playerArea) {
        this.root = root;
        this.playerArea = playerArea;
    }

    public static NexxPlayerViewHost create(Context context) {
        final RelativeLayout root = new RelativeLayout(context);
        root.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        root.setBackgroundColor(Color.BLACK);
        final FrameLayout playerArea = new FrameLayout(context);
        playerArea.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        root.addView(playerArea);
        return new NexxPlayerViewHost(root, playerArea);
    }

    public RelativeLayout getRoot() {
        return root;
    }

    public FrameLayout getPlayerArea() {
        return playerArea;
    }
}
