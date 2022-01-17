package tv.nexx.android.play;

public class PrivilegedConfiguration {
    private final NexxPLAY nexxPlay;

    private PrivilegedConfiguration(NexxPLAY nexxPlay) {
        this.nexxPlay = nexxPlay;
    }

    public static PrivilegedConfiguration of(NexxPLAY nexxPlay) {
        return new PrivilegedConfiguration(nexxPlay);
    }

    public void apply() {
        nexxPlay.setFlutter();
    }
}
