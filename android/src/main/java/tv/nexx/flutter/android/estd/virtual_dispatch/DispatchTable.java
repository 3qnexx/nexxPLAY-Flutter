package tv.nexx.flutter.android.estd.virtual_dispatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// I - identifier
// R - receiver
// P - payload
public class DispatchTable<I, R, P> {
    private final Map<I, DispatchTableMethod<R, P>> table;

    private DispatchTable(Map<I, DispatchTableMethod<R, P>> table) {
        this.table = Objects.requireNonNull(table);
    }

    public static <I, R, P> DispatchTable<I, R, P> threadConfined() {
        return new DispatchTable<>(new HashMap<>());
    }

    public void set(I identifier, DispatchTableMethod<R, P> method) {
        table.put(identifier, method);
    }

    public void dispatch(I identifier, R receiver, P payload) {
        final DispatchTableMethod<R, P> method = table.get(identifier);
        if (method == null) {
            throw new UndefinedDispatchTableMethodException(identifier);
        } else {
            method.dispatch(receiver, payload);
        }
    }

}
