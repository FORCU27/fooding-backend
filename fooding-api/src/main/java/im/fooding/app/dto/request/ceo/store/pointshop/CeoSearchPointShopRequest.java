package im.fooding.app.dto.request.ceo.store.pointshop;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.pointshop.PointShopSortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CeoSearchPointShopRequest extends BasicSearch {
    @Schema(description = "판매여부", example = "true")
    private Boolean isActive;

    @Schema(
            description = "정렬 타입(RECENT, OLD)",
            example = "RECENT",
            allowableValues = {"RECENT", "OLD"}
    )
    private PointShopSortType sortType = PointShopSortType.RECENT;
}
