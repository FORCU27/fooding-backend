package im.fooding.app.dto.request.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PosUpdateWaitingTimeRequest(
        @NotNull
        @Schema(description = "팀 당 예상 시간(분)", example = "1")
        Integer estimatedWaitingTimeMinutes
) {
}
