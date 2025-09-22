package im.fooding.app.dto.request.ceo.store.image;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.store.StoreImageSortType;
import im.fooding.core.model.store.StoreImageTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CeoSearchStoreImageRequest extends BasicSearch {
    @Schema(description = "태그", example = "PRICE_TAG")
    private StoreImageTag tag;

    @Schema(description = "대표사진 여부", example = "true")
    private Boolean isMain;

    @Schema(
            description = "정렬 타입",
            example = "RECENT",
            allowableValues = {"RECENT"}
    )
    private StoreImageSortType sortType = StoreImageSortType.RECENT;
}
