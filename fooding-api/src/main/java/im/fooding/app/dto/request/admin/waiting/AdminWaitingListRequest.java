package im.fooding.app.dto.request.admin.waiting;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class AdminWaitingListRequest extends BasicSearch {

    @Schema(description = "가게 ID로 필터", example = "1")
    Long storeId;

    @Schema(description = "웨이팅 상태 (WAITING_OPEN, IMMEDIATE_ENTRY, PAUSED, WAITING_CLOSE)")
    String status;
}

