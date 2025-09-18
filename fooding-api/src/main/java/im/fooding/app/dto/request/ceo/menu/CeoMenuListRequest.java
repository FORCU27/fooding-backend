package im.fooding.app.dto.request.ceo.menu;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class CeoMenuListRequest extends BasicSearch {

    @Schema(description = "가게 ID로 필터", example = "1")
    Long storeId;

    Long categoryId;
}

