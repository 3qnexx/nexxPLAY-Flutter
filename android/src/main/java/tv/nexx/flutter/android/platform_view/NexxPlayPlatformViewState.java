package tv.nexx.flutter.android.platform_view;

import android.view.ViewGroup;

import androidx.lifecycle.Lifecycle;

import com.google.android.gms.cast.framework.CastContext;

import java.util.Objects;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import tv.nexx.android.play.PrivilegedConfiguration;
import tv.nexx.android.play.NexxPLAY;
import tv.nexx.android.play.NexxPLAYEnvironment;
import tv.nexx.flutter.android.android.event.AndroidEvent;
import tv.nexx.flutter.android.estd.functional.Either;
import tv.nexx.flutter.android.estd.functional.Supplier;
import tv.nexx.flutter.android.estd.observer.Subject;
import tv.nexx.flutter.android.meta.Nullable;

public class NexxPlayPlatformViewState {

    private final NexxPlayInstanceID id;
    private final MethodChannel methodChannel;
    private final EventChannel eventChannel;
    private final Supplier<Lifecycle> lifecycle;
    private final NexxPLAYEnvironment environment;
    private final Subject<AndroidEvent> subject;
    @Nullable
    private final Either<Exception, CastContext> castContext;
    // Mutable (and nullable) due to the PlatformView#dispose contract
    @Nullable
    private EventChannel.EventSink sink;
    @Nullable
    private ViewGroup host;
    @Nullable
    private NexxPLAY player;
    @Nullable
    private NexxPlayLifecycleAdapter lifecycleAdapter;
    @Nullable
    private AndroidEventNexxPlayAdapter eventAdapter;

    NexxPlayPlatformViewState(Supplier<Lifecycle> lifecycle,
                              Subject<AndroidEvent> subject,
                              NexxPlayInstanceID id,
                              NexxPLAYEnvironment environment,
                              @Nullable Either<Exception, CastContext> castContext,
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
        this.castContext = castContext;
        this.host = host;
        this.player = player;
        this.lifecycleAdapter = NexxPlayLifecycleAdapter.of(player);
        this.eventAdapter = AndroidEventNexxPlayAdapter.of(player);
    }

    // Method exists to prevent object leaking in the constructor
    // https://stackoverflow.com/a/9851843/7884542
    void initialize(NexxPlayPlatformView view) {
        PrivilegedConfiguration.of(player).apply();
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

    @Nullable
    public EventChannel.EventSink sink() {
        return sink;
    }

    @Nullable
    public ViewGroup host() {
        return host;
    }

    @Nullable
    public NexxPLAY player() {
        return player;
    }

    @Nullable
    public Either<Exception, CastContext> castContext() {
        return castContext;
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
