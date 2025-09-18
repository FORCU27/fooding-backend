package im.fooding.app.dto.request.admin.waiting;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class AdminWaitingSettingListRequest extends BasicSearch {

    @Schema(description = "StoreService ID로 필터", example = "1")
    Long storeServiceId;

    @Schema(description = "활성화 여부로 필터", example = "true")
    Boolean isActive;
}

