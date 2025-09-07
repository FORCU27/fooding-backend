package im.fooding.app.dto.request.admin.waiting;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class AdminWaitingSettingListRequest extends BasicSearch {

    @Schema(description = "웨이팅 ID로 필터", example = "1")
    Long waitingId;

    @Schema(description = "활성화 여부로 필터", example = "true")
    Boolean isActive;
}

