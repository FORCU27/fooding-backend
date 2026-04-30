package im.fooding.app.dto.response.app.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class AppWaitingRegisterResponseV3 {
    @Schema(description = "요청 추적 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    String traceId;

    @Schema(description = "대기열 상태", example = "QUEUED")
    String status;
}
