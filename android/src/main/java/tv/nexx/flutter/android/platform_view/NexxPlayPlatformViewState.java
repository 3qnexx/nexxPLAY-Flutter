package tv.nexx.flutter.android.platform_view;

import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import java.util.Objects;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import tv.nexx.android.play.HiddenConfiguration;
import tv.nexx.android.play.NexxPLAY;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.estd.functional.Supplier;
import tv.nexx.flutter.android.estd.observer.Subject;

class NexxPlayPlatformViewState {

    private final NexxPlayInstanceID id;
    private final MethodChannel methodChannel;
    private final EventChannel eventChannel;
    private final Supplier<Lifecycle> lifecycle;
    private final NexxPlayInitializationArguments configuration;
    private final Subject<AndroidEvent> subject;
    // Mutable due to the PlatformView#dispose contract
    @Nullable
    private EventChannel.EventSink sink;
    @Nullable
    private ViewGroup host;
    @Nullable
    private NexxPLAY player;

    NexxPlayPlatformViewState(Supplier<Lifecycle> lifecycle,
                              Subject<AndroidEvent> subject,
                              NexxPlayInstanceID id,
                              NexxPlayInitializationArguments configuration,
                              MethodChannel methodChannel,
                              EventChannel eventChannel,
                              ViewGroup host,
                              NexxPLAY player) {
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
    void initialize(NexxPlayPlatformView view) {
        HiddenConfiguration.of(player).apply();
        final Lifecycle lifecycle = this.lifecycle.get();
        Objects.requireNonNull(lifecycle, "Lifecycle is null, normal operation is disrupted.");
        methodChannel.setMethodCallHandler(view);
        eventChannel.setStreamHandler(view);
        lifecycle.addObserver(view);
        subject.subscribe(view);
    }

    NexxPlayInstanceID id() {
        return id;
    }

    NexxPlayInitializationArguments initializationArguments() {
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

    NexxPLAY player() {
        return player;
    }

    void dispose(NexxPlayPlatformView view) {
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
