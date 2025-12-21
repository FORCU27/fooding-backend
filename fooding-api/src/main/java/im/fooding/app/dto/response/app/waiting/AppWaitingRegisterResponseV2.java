package im.fooding.app.dto.response.app.waiting;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record AppWaitingRegisterResponseV2(
        @Schema(description = "호출 번호", example = "1", requiredMode = REQUIRED)
        long callNumber,

        @Schema(description = "현재 웨이팅", example = "10", requiredMode = REQUIRED)
        long waitingTurn,

        @Schema(description = "예상 시간(분)", example = "100", requiredMode = REQUIRED)
        long expectedTimeMinute,

        @Schema(description = "최근 입장(분)", example = "5", requiredMode = REQUIRED)
        long recentEntryTimeMinute
) {
}
