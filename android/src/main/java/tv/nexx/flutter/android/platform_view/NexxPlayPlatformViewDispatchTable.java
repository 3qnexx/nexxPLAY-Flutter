package tv.nexx.flutter.android.platform_view;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTable;

class NexxPlayPlatformViewDispatchTable {

    private static final DispatchTable<String, NexxPlayPlatformView, NexxPlayDispatchPayload> DISPATCH_TABLE = DispatchTable.threadConfined();

    static {
        DISPATCH_TABLE.set("start", NexxPlayPlatformView::start);
    }

    private NexxPlayPlatformViewDispatchTable() {
    }

    // Thread-safe initialization via classloader
    private static class InstanceHolder {
        private static final NexxPlayPlatformViewDispatchTable INSTANCE =
                new NexxPlayPlatformViewDispatchTable();
    }

    static NexxPlayPlatformViewDispatchTable get() {
        return InstanceHolder.INSTANCE;
    }

    public void dispatch(String name, NexxPlayPlatformView view, NexxPlayDispatchPayload args) {
        DISPATCH_TABLE.dispatch(name, view, args);
    }

}
