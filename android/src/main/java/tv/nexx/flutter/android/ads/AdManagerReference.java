package tv.nexx.flutter.android.ads;

import android.content.Context;

import tv.nexx.android.admanager.NexxPlayAdManager;
import tv.nexx.android.play.ima.INexxPlayAdManager;

public class AdManagerReference {
    private INexxPlayAdManager adManager;

    private AdManagerReference() {

    }

    public static AdManagerReference create() {
        return new AdManagerReference();
    }

    public INexxPlayAdManager get(Context context) {
        final INexxPlayAdManager manager = this.adManager;
        // Typical double-checked locking.
        if (manager == null) {
            synchronized (this) {
                if (this.adManager == null) {
                    this.adManager = new NexxPlayAdManager(context);
                }
                return this.adManager;
            }
        } else {
            return manager;
        }
    }
}
