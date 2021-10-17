package tv.nexx.flutter.android.platform_view;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tv.nexx.android.play.Event;
import tv.nexx.android.play.NexxPLAYNotification.IPlayerEvent;

class DirectPlayerEvent implements CallResult {
    private final NexxPlayerInstanceID id;
    private final IPlayerEvent event;

    private DirectPlayerEvent(NexxPlayerInstanceID id, IPlayerEvent event) {
        this.id = id;
        this.event = event;
    }

    static DirectPlayerEvent of(NexxPlayerInstanceID id, IPlayerEvent event) {
        return new DirectPlayerEvent(id, event);
    }

    @Override
    public Map<String, Object> asMap() {
        final Map<String, Object> body = preprocessEventBody(event);
        return NexxPlayerMethodResult.from(id.numeric())
                .put("properties", body)
                .put("player_event_type", "player_event")
                .asMap();
    }

    @NonNull
    private Map<String, Object> preprocessEventBody(IPlayerEvent e) {
        final Map<String, Object> body = new HashMap<>(e.getBody());
        final Object event = body.get(tv.nexx.android.play.PlayerEvent.EVENT);
        if (event != null) {
            body.put(tv.nexx.android.play.PlayerEvent.EVENT, ((Event) event).name());
        }
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectPlayerEvent that = (DirectPlayerEvent) o;
        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DirectPlayerEvent{" +
                "id=" + id +
                ", event=" + event +
                '}';
    }
}
