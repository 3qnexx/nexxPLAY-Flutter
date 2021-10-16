package tv.nexx.flutter.android.estd.observer;

public interface MutableSubject<T> extends Subject<T> {
    void publish(T object);
}
