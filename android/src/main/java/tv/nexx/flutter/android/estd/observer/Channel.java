package tv.nexx.flutter.android.estd.observer;

import java.util.ArrayList;
import java.util.Collection;

import tv.nexx.flutter.android.estd.functional.Consumer;

public class Channel<T> implements MutableSubject<T> {

    private final Collection<Consumer<T>> consumers;

    private Channel(Collection<Consumer<T>> consumers) {
        this.consumers = consumers;
    }

    public static <T> Channel<T> threadConfined() {
        return new Channel<>(new ArrayList<>());
    }

    public void subscribe(Consumer<T> consumer) {
        consumers.add(consumer);
    }

    public void unsubscribe(Consumer<T> consumer) {
        consumers.remove(consumer);
    }

    @Override
    public void publish(T object) {
        for (Consumer<T> consumer : consumers) consumer.accept(object);
    }
}
