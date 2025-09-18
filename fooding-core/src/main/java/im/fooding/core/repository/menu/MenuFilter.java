package im.fooding.core.repository.menu;

import java.util.List;
import lombok.Builder;

@Builder
public record MenuFilter(
        Long storeId,
        List<Long> menuCategoryIds
) {
}
