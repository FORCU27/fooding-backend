package im.fooding.app.dto.request.admin.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AdminStoreWaitingUpdateRequest(

        @Schema(description = "웨이팅 유저 id", example = "1")
        Long waitingUserId,

        @Schema(description = "유저 ID", example = "1")
        Long userId,

        @Schema(description = "가게 id", example = "1")
        @NotNull
        Long storeId,

        @Schema(description = "웨이팅 상태(WAITING, SEATED, CANCELLED)", example = "WAITING")
        @NotNull
        String status,

        @Schema(description = "등록 방법(IN_PERSON, ONLINE)", example = "IN_PERSON")
        @NotNull
        String channel,

        @Schema(description = "유아용 의자 개수", example = "1")
        @NotNull
        Integer infantChairCount,

        @Schema(description = "유아 수", example = "1")
        @NotNull
        Integer infantCount,

        @Schema(description = "성인 수", example = "1")
        @NotNull
        Integer adultCount,

        @Schema(description = "메모", example = "메모 내용입니다.")
        @NotNull
        String memo
) {
}
