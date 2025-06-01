package im.fooding.app.dto.response.app.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class AppWaitingOverviewResponse {

    @Schema(description = "현재 웨이팅 수")
    int waitingCount;

    @Schema(description = "예상 시간(분)")
    int estimatedWaitingTimeMinutes;
}
