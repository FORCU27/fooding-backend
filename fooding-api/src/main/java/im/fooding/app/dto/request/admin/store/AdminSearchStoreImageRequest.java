package im.fooding.app.dto.request.admin.store;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.store.StoreImageTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSearchStoreImageRequest extends BasicSearch {
    @Schema(description = "검색 태그", example = "PRICE_TAG")
    private StoreImageTag tag;
    
    @Schema(description = "스토어 ID", example = "1")
    private Long storeId;

    @Schema(description = "대표사진 여부", example = "true")
    private Boolean isMain;
}

