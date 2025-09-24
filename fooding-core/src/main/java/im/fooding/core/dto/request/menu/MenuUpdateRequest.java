package im.fooding.core.dto.request.menu;

import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.store.Store;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record MenuUpdateRequest(
        long id,
        Store store,
        MenuCategory category,
        String name,
        int price,
        String description,
        List<String> imageUrls,
        int sortOrder,
        Boolean isSignature,
        Boolean isRecommend
) {
}
