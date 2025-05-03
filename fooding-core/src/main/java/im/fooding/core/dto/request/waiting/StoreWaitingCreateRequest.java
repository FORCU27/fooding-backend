package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import im.fooding.core.model.waiting.WaitingUser;
import lombok.Builder;

@Builder
public record StoreWaitingCreateRequest(
        WaitingUser user,
        Store store,
        String status,
        String channel,
        Integer infantChairCount,
        Integer infantCount,
        Integer adultCount,
        String memo
) {
    public StoreWaiting toStoreWaiting() {
        return StoreWaiting.builder()
                .user(user)
                .store(store)
                .status(StoreWaitingStatus.of(status))
                .channel(StoreWaitingChannel.of(channel))
                .infantChairCount(infantChairCount)
                .infantCount(infantCount)
                .adultCount(adultCount)
                .memo(memo)
                .build();
    }
}
