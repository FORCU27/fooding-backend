package im.fooding.core.dto.request.menu;

import im.fooding.core.model.menu.MenuCategory;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record MenuUpdateRequest(
        long id,
        MenuCategory category,
        String name,
        BigDecimal price,
        String description,
        String imageUrl,
        int sortOrder,
        Boolean isSignature,
        Boolean isRecommend
) {
}
