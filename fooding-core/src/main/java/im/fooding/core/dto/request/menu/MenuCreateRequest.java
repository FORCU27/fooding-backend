package im.fooding.core.dto.request.menu;

import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.store.Store;
import lombok.Builder;

@Builder
public record MenuCreateRequest(
        Store store,
        MenuCategory category,
        String name,
        int price,
        String description,
        int sortOrder,
        Boolean isSignature,
        Boolean isRecommend
) {
}
