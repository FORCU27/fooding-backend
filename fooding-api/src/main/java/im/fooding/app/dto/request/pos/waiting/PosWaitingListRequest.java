package im.fooding.app.dto.request.pos.waiting;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class PosWaitingListRequest extends BasicSearch {

    @Schema(description = "상태 (WAITING, SEATED, CANCELLED)", example = "WAITING")
    StoreWaitingStatus status;
}
