package im.fooding.core.event.waiting;

public record StoreWaitingRegisteredEvent(
        long storeWaitingId,
        Long userId,
        Long waitingUserId
) {
}
