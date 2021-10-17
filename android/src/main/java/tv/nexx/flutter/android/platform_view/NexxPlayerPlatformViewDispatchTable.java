package tv.nexx.flutter.android.platform_view;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTable;

class NexxPlayerPlatformViewDispatchTable {

    private static final DispatchTable<String, NexxPlayerPlatformView, NexxPlayerDispatchPayload> DISPATCH_TABLE = DispatchTable.threadConfined();

    static {
        DISPATCH_TABLE.set("start", NexxPlayerPlatformView::start);
    }

    private NexxPlayerPlatformViewDispatchTable() {
    }

    // Thread-safe initialization via classloader
    private static class InstanceHolder {
        private static final NexxPlayerPlatformViewDispatchTable INSTANCE =
                new NexxPlayerPlatformViewDispatchTable();
    }

    static NexxPlayerPlatformViewDispatchTable get() {
        return InstanceHolder.INSTANCE;
    }

    public void dispatch(String name, NexxPlayerPlatformView view, NexxPlayerDispatchPayload args) {
        DISPATCH_TABLE.dispatch(name, view, args);
    }

}
