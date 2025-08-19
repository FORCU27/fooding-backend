package im.fooding.core.event.store;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreCreatedEvent {
    public String name;

    public StoreCreatedEvent(String name) {
        this.name = name;
    }
}
