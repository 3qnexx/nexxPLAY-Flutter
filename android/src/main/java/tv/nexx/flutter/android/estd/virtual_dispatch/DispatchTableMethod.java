package tv.nexx.flutter.android.estd.virtual_dispatch;

@FunctionalInterface
public interface DispatchTableMethod<R, P> {
    void dispatch(R receiver, P payload);
}
