package im.fooding.app.dto.request.admin.waiting;

import im.fooding.core.dto.request.waiting.StoreWaitingCreateRequest;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminStoreWaitingCreateRequest(

        @Schema(description = "웨이팅 유저 id", example = "1")
        Long userId,

        @Schema(description = "가게 id", example = "1")
        @NotNull
        Long storeId,

        @Schema(description = "웨이팅 상태(WAITING, SEATED, CANCELLED)", example = "WAITING")
        @NotBlank
        String status,

        @Schema(description = "등록 방법(IN_PERSON, ONLINE)", example = "IN_PERSON")
        @NotBlank
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

        public StoreWaitingCreateRequest toStoreWaitingCreateRequest(WaitingUser user, Store store) {
                return StoreWaitingCreateRequest.builder()
                        .user(user)
                        .store(store)
                        .status(status)
                        .channel(channel)
                        .infantChairCount(infantChairCount)
                        .infantCount(infantCount)
                        .adultCount(adultCount)
                        .memo(memo)
                        .build();
        }
}
