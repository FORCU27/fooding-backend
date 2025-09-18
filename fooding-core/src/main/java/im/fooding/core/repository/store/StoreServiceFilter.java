package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreServiceType;
import lombok.Builder;

@Builder
public record StoreServiceFilter(
        Long storeId,
        StoreServiceType type
) {
}
