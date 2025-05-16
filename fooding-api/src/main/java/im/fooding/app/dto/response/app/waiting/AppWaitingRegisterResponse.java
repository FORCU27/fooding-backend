package im.fooding.app.dto.response.app.waiting;

import io.swagger.v3.oas.annotations.media.Schema;

public record AppWaitingRegisterResponse(
        @Schema(description = "호출 번호", example = "1")
        long callNumber
) {
}
