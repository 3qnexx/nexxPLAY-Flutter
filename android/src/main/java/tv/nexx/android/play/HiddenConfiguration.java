package tv.nexx.android.play;

public class HiddenConfiguration {
    private final NexxPLAY nexxPlay;

    private HiddenConfiguration(NexxPLAY nexxPlay) {
        this.nexxPlay = nexxPlay;
    }

    public static HiddenConfiguration of(NexxPLAY nexxPlay) {
        return new HiddenConfiguration(nexxPlay);
    }

    public void apply() {
        nexxPlay.setFlutter();
    }
}
