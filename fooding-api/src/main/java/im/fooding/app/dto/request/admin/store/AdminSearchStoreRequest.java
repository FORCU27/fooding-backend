package im.fooding.app.dto.request.admin.store;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.store.StoreSortType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.SortDirection;

@Getter
@Setter
@NoArgsConstructor
public class AdminSearchStoreRequest extends BasicSearch {
    @Schema(
            description = "정렬 타입",
            example = "RECENT",
            allowableValues = {"RECENT", "AVERAGE_RATING", "REVIEW", "VISIT"}
    )
    private StoreSortType sortType = StoreSortType.RECENT;

    @Schema(
            description = "정렬 순서",
            example = "ASCENDING",
            allowableValues = {"ASCENDING", "DESCENDING"}
    )
    private SortDirection sortDirection = SortDirection.DESCENDING;
}
