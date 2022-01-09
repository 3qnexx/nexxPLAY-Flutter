package tv.nexx.flutter.android.estd.functional;

class Right<T, R> implements Either<T, R> {
    private final R value;

    Right(R value) {
        this.value = value;
    }

    @Override
    public <U> Either<T, U> map(Function<R, U> function) {
        return new Right<>(function.apply(value));
    }

    @Override
    public <U> Either<T, U> flatMap(Function<R, Either<T, U>> function) {
        return function.apply(value);
    }

    @Override
    public <U> U fold(Function<T, U> left, Function<R, U> right) {
        return right.apply(value);
    }

    @Override
    public void consume(Consumer<T> left, Consumer<R> right) {
        right.accept(value);
    }
}
