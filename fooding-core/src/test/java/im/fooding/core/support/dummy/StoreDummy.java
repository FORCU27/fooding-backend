package im.fooding.core.support.dummy;

import im.fooding.core.model.store.Store;

public class StoreDummy {

    public static Store create() {
        return Store.builder()
                .name("name")
                .city("city")
                .address("address")
                .category("category")
                .description("description")
                .contactNumber("01012345678")
                .priceCategory("priceCategory")
                .eventDescription("eventDescription")
                .direction("direction")
                .information("information")
                .isParkingAvailable(true)
                .isNewOpen(true)
                .isTakeOut(true)
                .build();
    }
}
