package im.fooding.app.dto.request.admin.store;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.store.StoreStatus;
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
            allowableValues = {"RECENT", "RECOMMENDED", "AVERAGE_RATING", "REVIEW",  "PRICE", "DISTANCE"}
    )
    private StoreSortType sortType = StoreSortType.RECENT;

    @Schema(
            description = "정렬 순서",
            example = "ASCENDING",
            allowableValues = {"ASCENDING", "DESCENDING"}
    )
    private SortDirection sortDirection = SortDirection.DESCENDING;

    @Schema(description = "검색 지역 ID 목록", example = "KR-11,KR-26")
    private java.util.List<String> regionIds;

    @Schema(description = "카테고리", example = "KOREAN")
    private StoreCategory category;

    @Schema(description = "조회할 상태들", example = "APPROVED")
    private java.util.Set<StoreStatus> statuses;

    @Schema(description = "삭제 포함 여부", example = "false")
    private Boolean includeDeleted = false;

    @Schema(description = "위도", example = "36.40947226931638")
    private Double latitude;

    @Schema(description = "경도", example = "127.12345678901234")
    private Double longitude;
}
