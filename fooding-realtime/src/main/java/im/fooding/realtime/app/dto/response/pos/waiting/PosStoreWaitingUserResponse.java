package im.fooding.realtime.app.dto.response.pos.waiting;

import im.fooding.realtime.app.domain.waiting.StoreWaitingUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PosStoreWaitingUserResponse(

        @Schema(description = "id", example = "1")
        long id,

        @Schema(description = "가게 id", example = "1")
        long storeId,

        @Schema(description = "이름", example = "홍길동")
        String name,

        @Schema(description = "전화번호", example = "01012345678")
        String phoneNumber,

        @Schema(description = "방문 횟수", example = "1")
        int count
) {
        public static PosStoreWaitingUserResponse from(StoreWaitingUser user) {
                return PosStoreWaitingUserResponse.builder()
                        .id(user.getId())
                        .storeId(user.getStoreId())
                        .name(user.getName())
                        .phoneNumber(user.getPhoneNumber())
                        .count(user.getCount())
                        .build();
        }
}
