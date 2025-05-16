package im.fooding.app.dto.request.pos.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PosWaitingStatusUpdateRequest(
        @NotBlank
        @Schema(description = "웨이팅 상태(WAITING_OPEN, IMMEDIATE_ENTRY, PAUSED, WAITING_CLOSE)", example = "PAUSED")
        String status
) {
}
