package tv.nexx.flutter.android.platform_view.command;

import tv.nexx.flutter.android.estd.virtual_dispatch.DispatchTable;
import tv.nexx.flutter.android.platform_view.NexxPlayDispatchPayload;
import tv.nexx.flutter.android.platform_view.NexxPlayPlatformView;

public class NexxPlayPlatformViewDispatchTable {

    private static final NexxPlayPlatformViewDispatchTable INSTANCE = new NexxPlayPlatformViewDispatchTable();
    private static final DispatchTable<String, NexxPlayPlatformView, NexxPlayDispatchPayload> DISPATCH_TABLE = DispatchTable.threadConfined();

    static {
        DISPATCH_TABLE.set("start", StartPlayerCommand.create());
        DISPATCH_TABLE.set("clearCache", ClearCacheCommand.create());
        DISPATCH_TABLE.set("updateConfiguration", UpdateConfigurationCommand.create());
        DISPATCH_TABLE.set("UpdateEnvironmentCommand", UpdateEnvironmentCommand.create());
    }

    private NexxPlayPlatformViewDispatchTable() {
    }

    public static NexxPlayPlatformViewDispatchTable get() {
        return INSTANCE;
    }

    public void dispatch(String name, NexxPlayPlatformView view, NexxPlayDispatchPayload args) {
        DISPATCH_TABLE.dispatch(name, view, args);
    }

}
