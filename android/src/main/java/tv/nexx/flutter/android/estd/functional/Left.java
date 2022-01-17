package tv.nexx.flutter.android.estd.functional;

class Left<T, R> implements Either<T, R> {
    private final T value;

    Left(T value) {
        this.value = value;
    }

    @Override
    public <U> Either<T, U> map(Function<R, U> function) {
        //noinspection unchecked
        return (Either<T, U>) this;
    }

    @Override
    public <U> Either<T, U> flatMap(Function<R, Either<T, U>> function) {
        //noinspection unchecked
        return (Either<T, U>) this;
    }

    @Override
    public <U> U fold(Function<T, U> left, Function<R, U> right) {
        return left.apply(value);
    }

    @Override
    public void consume(Consumer<T> left, Consumer<R> right) {
        left.accept(value);
    }
}
