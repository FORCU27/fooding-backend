package im.fooding.core.event.waiting;

public record StoreWaitingCanceledEvent(
        long storeWaitingId,
        String reason
) {
}
