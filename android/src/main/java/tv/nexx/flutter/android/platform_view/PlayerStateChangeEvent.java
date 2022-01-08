package tv.nexx.flutter.android.platform_view;

import java.util.Map;

import tv.nexx.android.play.player.IPlayer.State;

class PlayerStateChangeEvent implements CallResult {

    private final NexxPlayInstanceID id;
    private final boolean playWhenReady;
    private final State state;

    private PlayerStateChangeEvent(NexxPlayInstanceID id, boolean playWhenReady, State state) {
        this.id = id;
        this.playWhenReady = playWhenReady;
        this.state = state;
    }

    static PlayerStateChangeEvent of(NexxPlayInstanceID id, boolean playWhenReady, State state) {
        return new PlayerStateChangeEvent(id, playWhenReady, state);
    }

    @Override
    public Map<String, Object> asMap() {
        return NexxPlayMethodResult.from(id.numeric())
                .put("player_event_type", "player_state_changed")
                .put("play_when_ready", playWhenReady)
                .put("state", state.name())
                .asMap();
    }
}
