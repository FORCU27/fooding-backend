package im.fooding.core.support.dummy;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import im.fooding.core.model.waiting.WaitingUser;

public class StoreWaitingDummy {

    public static StoreWaiting create(WaitingUser waitingUser, Store store) {
        return StoreWaiting.builder()
                .waitingUser(waitingUser)
                .store(store)
                .callNumber(1)
                .status(StoreWaitingStatus.WAITING)
                .channel(StoreWaitingChannel.IN_PERSON)
                .infantChairCount(1)
                .infantCount(1)
                .adultCount(1)
                .memo("memo")
                .build();
    }
}
