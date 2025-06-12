package im.fooding.app.dto.request.ceo.store;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CeoSearchStoreImageRequest extends BasicSearch {
    @NotNull
    @Schema(description = "가게 id", example = "1")
    private Long storeId;

    @Schema(description = "검색 태그", example = "업체")
    private String searchTag;
}
