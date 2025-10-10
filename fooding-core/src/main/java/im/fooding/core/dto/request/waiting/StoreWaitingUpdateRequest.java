package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.model.waiting.WaitingUser;
import lombok.Builder;

@Builder
public record StoreWaitingUpdateRequest(
        Long id,
        WaitingUser waitingUser,
        User user,
        Store store,
        String status,
        String channel,
        Integer infantChairCount,
        Integer infantCount,
        Integer adultCount,
        String memo
) {
}
