package tv.nexx.flutter.android.estd.functional;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(T first, U second, V third);
}
