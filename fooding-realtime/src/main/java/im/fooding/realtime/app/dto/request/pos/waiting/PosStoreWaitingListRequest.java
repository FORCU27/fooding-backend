package im.fooding.realtime.app.dto.request.pos.waiting;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PosStoreWaitingListRequest extends BasicSearch {

    @Schema(description = "상태 (WAITING, SEATED, CANCELLED)", example = "WAITING")
    StoreWaitingStatus status;
}
