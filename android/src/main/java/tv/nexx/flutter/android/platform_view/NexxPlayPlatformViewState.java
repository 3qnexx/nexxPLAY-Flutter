package tv.nexx.flutter.android.platform_view;

import android.view.ViewGroup;

import androidx.lifecycle.Lifecycle;

import java.util.Objects;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import tv.nexx.android.play.HiddenConfiguration;
import tv.nexx.android.play.NexxPLAY;
import tv.nexx.android.play.NexxPLAYEnvironment;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.estd.functional.Supplier;
import tv.nexx.flutter.android.estd.observer.Subject;

public class NexxPlayPlatformViewState {

    private final NexxPlayInstanceID id;
    private final MethodChannel methodChannel;
    private final EventChannel eventChannel;
    private final Supplier<Lifecycle> lifecycle;
    private final NexxPLAYEnvironment environment;
    private final Subject<AndroidEvent> subject;
    // Mutable (and nullable) due to the PlatformView#dispose contract
    private EventChannel.EventSink sink;
    private ViewGroup host;
    private NexxPLAY player;
    private NexxPlayLifecycleAdapter lifecycleAdapter;
    private AndroidEventNexxPlayAdapter eventAdapter;

    NexxPlayPlatformViewState(Supplier<Lifecycle> lifecycle,
                              Subject<AndroidEvent> subject,
                              NexxPlayInstanceID id,
                              NexxPLAYEnvironment environment,
                              MethodChannel methodChannel,
                              EventChannel eventChannel,
                              ViewGroup host,
                              NexxPLAY player) {
        this.id = id;
        this.methodChannel = methodChannel;
        this.eventChannel = eventChannel;
        this.lifecycle = lifecycle;
        this.environment = environment;
        this.subject = subject;
        this.host = host;
        this.player = player;
        this.lifecycleAdapter = NexxPlayLifecycleAdapter.of(player);
        this.eventAdapter = AndroidEventNexxPlayAdapter.of(player);
    }

    // Method exists to prevent object leaking in the constructor
    // https://stackoverflow.com/a/9851843/7884542
    void initialize(NexxPlayPlatformView view) {
        HiddenConfiguration.of(player).apply();
        final Lifecycle lifecycle = this.lifecycle.get();
        Objects.requireNonNull(lifecycle, "Lifecycle is null, normal operation is disrupted.");
        methodChannel.setMethodCallHandler(view);
        eventChannel.setStreamHandler(view);
        lifecycle.addObserver(lifecycleAdapter);
        subject.subscribe(eventAdapter);
    }

    public NexxPlayInstanceID id() {
        return id;
    }

    public NexxPLAYEnvironment environment() {
        return environment;
    }

    public EventChannel.EventSink sink() {
        return sink;
    }

    public ViewGroup host() {
        return host;
    }

    public NexxPLAY player() {
        return player;
    }

    public boolean isDisposed() {
        return player == null;
    }

    void sink(EventChannel.EventSink sink) {
        this.sink = sink;
    }

    void dispose(NexxPlayPlatformView view) {
        subject.unsubscribe(eventAdapter);
        final Lifecycle lifecycle = this.lifecycle.get();
        if (lifecycle != null && lifecycleAdapter != null) {
            lifecycle.removeObserver(lifecycleAdapter);
        }
        eventChannel.setStreamHandler(null);
        methodChannel.setMethodCallHandler(null);
        eventAdapter = null;
        lifecycleAdapter = null;
        sink = null;
        if (player != null) {
            player.removePlaystateListener(view);
            player.onActivityDestroyed();
        }
        player = null;
        host = null;
    }
}
