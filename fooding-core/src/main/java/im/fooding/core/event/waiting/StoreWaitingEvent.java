package im.fooding.core.event.waiting;

import lombok.Builder;

@Builder
public record StoreWaitingEvent(
        long storeId,
        long storeWaitingId,
        Type type
) {

    public enum Type {
        CREATED,
        UPDATED,
        DELETED,
    }
}
