package tv.nexx.flutter.android.platform_view;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import tv.nexx.android.play.INexxPLAY;

class NexxPlayLifecycleAdapter implements LifecycleObserver {
    private final INexxPLAY player;

    private NexxPlayLifecycleAdapter(INexxPLAY player) {
        this.player = player;
    }

    public static NexxPlayLifecycleAdapter of(INexxPLAY player) {
        return new NexxPlayLifecycleAdapter(player);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        player.onActivityResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        player.onActivityPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        player.onActivityDestroyed();
    }
}
