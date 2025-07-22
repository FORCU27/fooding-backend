package im.fooding.app.dto.response.pos.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import io.swagger.v3.oas.annotations.media.Schema;

public record PosStoreWaitingResponse(
        @Schema(description = "id", example = "1")
        long id,

        @Schema(description = "가게 id", example = "1")
        long storeId,

        @Schema(description = "유저 정보")
        PosWaitingUserResponse user,

        @Schema(description = "호출 번호", example = "1")
        int callNumber,

        @Schema(description = "등록 수단", example = "IN_PERSON")
        String channel,

        @Schema(description = "필요한 유아용 의자 개수", example = "1")
        int infantChairCount,

        @Schema(description = "유아 입장 인원수", example = "1")
        int infantCount,

        @Schema(description = "성인 입장 인원수", example = "1")
        int adultCount,

        @Schema(description = "메모", example = "this is memo.")
        String memo
) {

    public static PosStoreWaitingResponse from(StoreWaiting storeWaiting) {
        return new PosStoreWaitingResponse(
                storeWaiting.getId(),
                storeWaiting.getStoreId(),
                PosWaitingUserResponse.from(storeWaiting.getWaitingUser()),
                storeWaiting.getCallNumber(),
                storeWaiting.getChannelValue(),
                storeWaiting.getInfantChairCount(),
                storeWaiting.getInfantCount(),
                storeWaiting.getAdultCount(),
                storeWaiting.getMemo()
        );
    }
}
