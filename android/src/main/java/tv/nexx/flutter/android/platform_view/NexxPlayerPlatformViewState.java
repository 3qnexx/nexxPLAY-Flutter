package tv.nexx.flutter.android.platform_view;

import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import java.util.Objects;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import tv.nexx.android.play.INexxPLAY;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.estd.functional.Supplier;
import tv.nexx.flutter.android.estd.observer.Subject;
import tv.nexx.flutter.android.nexx_player.NexxPlayerConfiguration;

class NexxPlayerPlatformViewState {

    private final NexxPlayerInstanceID id;
    private final MethodChannel methodChannel;
    private final EventChannel eventChannel;
    private final Supplier<Lifecycle> lifecycle;
    private final NexxPlayerConfiguration configuration;
    private final Subject<AndroidEvent> subject;
    // Mutable due to the PlatformView#dispose contract
    @Nullable
    private EventChannel.EventSink sink;
    @Nullable
    private ViewGroup host;
    @Nullable
    private INexxPLAY player;

    NexxPlayerPlatformViewState(Supplier<Lifecycle> lifecycle,
                                Subject<AndroidEvent> subject,
                                NexxPlayerInstanceID id,
                                NexxPlayerConfiguration configuration,
                                MethodChannel methodChannel,
                                EventChannel eventChannel,
                                ViewGroup host,
                                INexxPLAY player) {
        this.id = id;
        this.methodChannel = methodChannel;
        this.eventChannel = eventChannel;
        this.lifecycle = lifecycle;
        this.configuration = configuration;
        this.subject = subject;
        this.host = host;
        this.player = player;
    }

    // Method exists to prevent object leaking in the constructor
    // https://stackoverflow.com/a/9851843/7884542
    void initialize(NexxPlayerPlatformView view) {
        final Lifecycle lifecycle = this.lifecycle.get();
        Objects.requireNonNull(lifecycle, "Lifecycle is null, normal operation is disrupted.");
        methodChannel.setMethodCallHandler(view);
        eventChannel.setStreamHandler(view);
        lifecycle.addObserver(view);
        subject.subscribe(view);
    }

    NexxPlayerInstanceID id() {
        return id;
    }

    NexxPlayerConfiguration configuration() {
        return configuration;
    }

    EventChannel.EventSink sink() {
        return sink;
    }

    void sink(EventChannel.EventSink sink) {
        this.sink = sink;
    }

    ViewGroup host() {
        return host;
    }

    INexxPLAY player() {
        return player;
    }

    void dispose(NexxPlayerPlatformView view) {
        subject.unsubscribe(view);
        final Lifecycle lifecycle = this.lifecycle.get();
        if (lifecycle != null) lifecycle.removeObserver(view);
        eventChannel.setStreamHandler(null);
        methodChannel.setMethodCallHandler(null);
        sink = null;
        if (player != null) {
            player.removePlaystateListener(view);
            player.onActivityDestroyed();
        }
        player = null;
        host = null;
    }

    boolean isDisposed() {
        return player == null;
    }
}
