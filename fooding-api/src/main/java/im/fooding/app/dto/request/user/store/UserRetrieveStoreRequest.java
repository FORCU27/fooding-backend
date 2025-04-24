package im.fooding.app.dto.request.user.store;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.store.StoreSortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.SortDirection;

@Getter
@Setter
@NoArgsConstructor
public class UserRetrieveStoreRequest extends BasicSearch {

    @NotNull
    @Schema(
            description = "정렬 타입",
            example = "RECENT",
            allowableValues = {"RECENT", "REVIEW"}
    )
    private StoreSortType sortType;

    @NotNull
    @Schema(
            description = "정렬 순서",
            example = "ASCENDING",
            allowableValues = {"ASCENDING", "DESCENDING"}
    )
    private SortDirection sortDirection;
}
