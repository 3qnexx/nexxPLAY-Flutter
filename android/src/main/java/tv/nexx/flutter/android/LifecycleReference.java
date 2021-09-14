package tv.nexx.flutter.android;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import tv.nexx.flutter.android.estd.functional.Consumer;
import tv.nexx.flutter.android.estd.functional.Supplier;

final class LifecycleReference implements Supplier<Lifecycle>, Consumer<Lifecycle> {
    @Nullable
    private Lifecycle lifecycle;

    private LifecycleReference(@Nullable Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public static LifecycleReference empty() {
        return new LifecycleReference(null);
    }

    @Override
    public void accept(Lifecycle value) {
        this.lifecycle = value;
    }

    @Override
    public Lifecycle get() {
        return lifecycle;
    }
}
