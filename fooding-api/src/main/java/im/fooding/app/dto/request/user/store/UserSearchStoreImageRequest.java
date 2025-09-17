package im.fooding.app.dto.request.user.store;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.store.StoreImageTag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchStoreImageRequest extends BasicSearch {
    @Schema(description = "검색 태그", example = "PRICE_TAG")
    private StoreImageTag tag;

    @Schema(description = "대표사진 여부", example = "false")
    private Boolean isMain;
}
