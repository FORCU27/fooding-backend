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
    @Schema(description = "정렬타입", example = "RECENT")
    private StoreSortType sortType;

    @Schema(description = "정렬방향", example = "DESCENDING")
    private SortDirection sortDirection;
}
