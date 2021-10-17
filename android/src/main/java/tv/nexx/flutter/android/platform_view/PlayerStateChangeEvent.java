package tv.nexx.flutter.android.platform_view;

import java.util.Map;

import tv.nexx.android.play.player.Player.State;

class PlayerStateChangeEvent implements CallResult {

    private final NexxPlayerInstanceID id;
    private final boolean playWhenReady;
    private final State state;

    private PlayerStateChangeEvent(NexxPlayerInstanceID id, boolean playWhenReady, State state) {
        this.id = id;
        this.playWhenReady = playWhenReady;
        this.state = state;
    }

    static PlayerStateChangeEvent of(NexxPlayerInstanceID id, boolean playWhenReady, State state) {
        return new PlayerStateChangeEvent(id, playWhenReady, state);
    }

    @Override
    public Map<String, Object> asMap() {
        return NexxPlayerMethodResult.from(id.numeric())
                .put("player_event_type", "player_state_changed")
                .put("play_when_ready", playWhenReady)
                .put("state", state.name())
                .asMap();
    }
}
