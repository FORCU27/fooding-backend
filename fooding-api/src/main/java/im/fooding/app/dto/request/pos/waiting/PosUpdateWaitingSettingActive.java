package im.fooding.app.dto.request.pos.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PosUpdateWaitingSettingActive(
        @NotNull
        @Schema(description = "활성 상태", example = "true")
        Boolean active
) {
}
