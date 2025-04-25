package im.fooding.app.dto.request.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PosWaitingCancelRequest(
        @NotBlank
        @Schema(description = "사유", example = "조기 마감으로 인해 웨이팅이 취소되었습니다.")
        String reason
) {
}
