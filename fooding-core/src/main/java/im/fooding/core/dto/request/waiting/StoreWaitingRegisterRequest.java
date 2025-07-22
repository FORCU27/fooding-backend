package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
import lombok.Builder;

@Builder
public record StoreWaitingRegisterRequest(
        WaitingUser waitingUser,
        Store store,
        String channel,
        int infantChairCount,
        int infantCount,
        int adultCount
) {
}
