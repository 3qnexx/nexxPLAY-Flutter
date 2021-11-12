package tv.nexx.flutter.android.estd.observer;

import tv.nexx.flutter.android.estd.functional.Consumer;

public interface Subject<T> {
    void subscribe(Consumer<T> observer);

    void unsubscribe(Consumer<T> observer);
}
