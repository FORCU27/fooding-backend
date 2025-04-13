package im.fooding.core.support.dummy;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.WaitingUser;

public class StoreWaitingDummy {

    public static StoreWaiting create(WaitingUser user, Store store) {
        return StoreWaiting.builder()
                .user(user)
                .store(store)
                .callNumber(1)
                .channel(StoreWaitingChannel.IN_PERSON)
                .infantChairCount(1)
                .infantCount(1)
                .adultCount(1)
                .build();
    }
}
