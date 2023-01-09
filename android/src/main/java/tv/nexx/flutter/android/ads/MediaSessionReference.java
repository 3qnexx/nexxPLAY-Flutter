package tv.nexx.flutter.android.ads;

import android.content.Context;
import android.support.v4.media.session.MediaSessionCompat;

public class MediaSessionReference {
    private MediaSessionCompat mediaSession;

    private MediaSessionReference() {

    }

    public static MediaSessionReference create() {
        return new MediaSessionReference();
    }

    public MediaSessionCompat get(Context context) {
        final MediaSessionCompat session = this.mediaSession;
        // Typical double-checked locking.
        if (session == null) {
            synchronized (this) {
                if (this.mediaSession == null) {
                    this.mediaSession = new MediaSessionCompat(context.getApplicationContext(), context.getPackageName());
                }
                return this.mediaSession;
            }
        } else {
            return session;
        }
    }
}
