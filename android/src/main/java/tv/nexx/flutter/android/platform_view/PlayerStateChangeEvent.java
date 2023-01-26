package tv.nexx.flutter.android.platform_view;

import java.util.Map;

import tv.nexx.android.play.player.IPlayer.State;

class PlayerStateChangeEvent implements CallResult {

    private final NexxPlayInstanceID id;
    private final State state;

    private PlayerStateChangeEvent(NexxPlayInstanceID id, State state) {
        this.id = id;
        this.state = state;
    }

    static PlayerStateChangeEvent of(NexxPlayInstanceID id, State state) {
        return new PlayerStateChangeEvent(id, state);
    }

    @Override
    public Map<String, Object> asMap() {
        return NexxPlayMethodResult.from(id.numeric())
                .put("player_event_type", "player_state_changed")
                .put("state", state.name())
                .asMap();
    }
}
