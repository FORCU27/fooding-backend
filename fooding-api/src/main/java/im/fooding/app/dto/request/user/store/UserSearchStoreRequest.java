package im.fooding.app.dto.request.user.store;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreSortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.SortDirection;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchStoreRequest extends BasicSearch {
    @Schema(
            description = "정렬 타입",
            example = "RECENT",
            allowableValues = {"RECENT", "RECOMMENDED", "AVERAGE_RATING", "REVIEW",  "PRICE", "DISTANCE"}
    )
    private StoreSortType sortType = StoreSortType.RECENT;

    @Schema(
            description = "정렬 순서",
            example = "ASCENDING",
            allowableValues = {"ASCENDING", "DESCENDING"}
    )
    private SortDirection sortDirection = SortDirection.DESCENDING;

    @Schema(description = "지역 ids", example = "[\"KR-11\",\"KR-11680101\"]")
    private List<String> regionIds;

    @Schema(description = "카테고리", example = "KOREAN")
    private StoreCategory category;

    @Schema(description = "위도", example = "36.40947226931638")
    private Double latitude;

    @Schema(description = "경도", example = "127.12345678901234")
    private Double longitude;
}
