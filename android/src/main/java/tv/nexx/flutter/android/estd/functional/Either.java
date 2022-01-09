package tv.nexx.flutter.android.estd.functional;

public interface Either<T, R> {

    static <T, R> Either<T, R> left(T value) {
        return new Left<>(value);
    }

    static <T, R> Either<T, R> right(R value) {
        return new Right<>(value);
    }

    <U> Either<T, U> map(Function<R, U> function);

    <U> Either<T, U> flatMap(Function<R, Either<T, U>> function);

    <U> U fold(Function<T, U> left, Function<R, U> right);

    void consume(Consumer<T> left, Consumer<R> right);

}
