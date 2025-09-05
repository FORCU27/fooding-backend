package im.fooding.app.dto.request.admin.store.image;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSearchStoreImageRequest extends BasicSearch {
    @Schema(description = "검색 태그", example = "업체")
    private String searchTag;
}

