package im.fooding.core.support.dummy;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;

public class WaitingDummy {

    public static Waiting create(Store store) {
        return new Waiting(store, WaitingStatus.WAITING_OPEN);
    }
}
