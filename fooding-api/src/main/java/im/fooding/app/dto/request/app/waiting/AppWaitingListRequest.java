package im.fooding.app.dto.request.app.waiting;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class AppWaitingListRequest extends BasicSearch {

    @Schema(description = "상태 (WAITING, SEATED, CANCELLED)", example = "WAITING")
    String status;
}

