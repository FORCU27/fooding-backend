package im.fooding.realtime.app.dto.response.pos.waiting;

import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.realtime.app.domain.waiting.StoreWaiting;
import im.fooding.realtime.app.domain.waiting.StoreWaitingUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PosStoreWaitingResponse(

        @Schema(description = "id", example = "1")
        long id,

        @Schema(description = "가게 id", example = "1")
        long storeId,

        @Schema(description = "유저 정보")
        PosStoreWaitingUserResponse user,

        @Schema(description = "호출 번호", example = "1")
        int callNumber,

        @Schema(description = "등록 수단", example = "IN_PERSON")
        StoreWaitingChannel channel,

        @Schema(description = "필요한 유아용 의자 개수", example = "1")
        int infantChairCount,

        @Schema(description = "유아 입장 인원수", example = "1")
        int infantCount,

        @Schema(description = "성인 입장 인원수", example = "1")
        int adultCount,

        @Schema(description = "메모", example = "this is memo.")
        String memo
) {

        public static PosStoreWaitingResponse from(StoreWaiting storeWaiting, StoreWaitingUser user) {
                PosStoreWaitingUserResponse userResponse = user == null ? null : PosStoreWaitingUserResponse.from(user);

                return PosStoreWaitingResponse.builder()
                        .id(storeWaiting.getId())
                        .storeId(storeWaiting.getStoreId())
                        .user(userResponse)
                        .callNumber(storeWaiting.getCallNumber())
                        .channel(storeWaiting.getChannel())
                        .infantChairCount(storeWaiting.getInfantChairCount())
                        .infantCount(storeWaiting.getInfantCount())
                        .adultCount(storeWaiting.getAdultCount())
                        .memo(storeWaiting.getMemo())
                        .build();
        }
}
