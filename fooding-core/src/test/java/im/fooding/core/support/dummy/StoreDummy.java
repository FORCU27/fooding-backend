package im.fooding.core.support.dummy;

import im.fooding.core.model.region.Region;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;

public class StoreDummy {

    public static Store create() {
        return Store.builder()
                .name("name")
                .address("address")
                .category(StoreCategory.KOREAN)
                .description("description")
                .contactNumber("01012345678")
                .direction("direction")
                .isNewOpen(true)
                .isTakeOut(true)
                .build();
    }

    public static Store create(Region region) {
        return Store.builder()
                .name("name")
                .region(region)
                .address("address")
                .category(StoreCategory.KOREAN)
                .description("description")
                .contactNumber("01012345678")
                .direction("direction")
                .isNewOpen(true)
                .isTakeOut(true)
                .build();
    }
}
