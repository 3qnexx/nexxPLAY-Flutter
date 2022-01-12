package tv.nexx.flutter.android.platform_view;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.Map;

import tv.nexx.android.play.INexxPLAY;
import tv.nexx.flutter.android.estd.functional.Consumer;

class NexxPlayLifecycleAdapter implements LifecycleEventObserver {

    private static final Map<Lifecycle.Event, Consumer<INexxPLAY>> MAPPING = new HashMap<>();

    static {
        MAPPING.put(Lifecycle.Event.ON_RESUME, INexxPLAY::onActivityResume);
        MAPPING.put(Lifecycle.Event.ON_PAUSE, INexxPLAY::onActivityPause);
        MAPPING.put(Lifecycle.Event.ON_DESTROY, INexxPLAY::onActivityDestroyed);
    }

    private final INexxPLAY player;

    private NexxPlayLifecycleAdapter(INexxPLAY player) {
        this.player = player;
    }

    public static NexxPlayLifecycleAdapter of(INexxPLAY player) {
        return new NexxPlayLifecycleAdapter(player);
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source,
                               @NonNull Lifecycle.Event event) {
        final Consumer<INexxPLAY> consumer = MAPPING.get(event);
        if (consumer != null) {
            consumer.accept(player);
        }
    }

}
