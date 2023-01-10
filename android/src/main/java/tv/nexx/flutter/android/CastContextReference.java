package tv.nexx.flutter.android;

import androidx.annotation.Nullable;

import com.google.android.gms.cast.framework.CastContext;

import tv.nexx.flutter.android.estd.functional.Consumer;
import tv.nexx.flutter.android.estd.functional.Either;
import tv.nexx.flutter.android.estd.functional.Supplier;

final class CastContextReference implements Supplier<Either<Throwable, CastContext>>, Consumer<Either<Throwable, CastContext>> {
    @Nullable
    private Either<Throwable, CastContext> castContext;

    private CastContextReference(@SuppressWarnings("SameParameterValue") @Nullable Either<Throwable, CastContext> castContext) {
        this.castContext = castContext;
    }

    public static CastContextReference empty() {
        return new CastContextReference(null);
    }

    @Override
    public void accept(Either<Throwable, CastContext> value) {
        this.castContext = value;
    }

    @Override
    public Either<Throwable, CastContext> get() {
        return castContext;
    }
}
