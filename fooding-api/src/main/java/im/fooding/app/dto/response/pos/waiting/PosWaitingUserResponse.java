package im.fooding.app.dto.response.pos.waiting;

import im.fooding.core.model.waiting.WaitingUser;
import io.swagger.v3.oas.annotations.media.Schema;

public record PosWaitingUserResponse(
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
    public static PosWaitingUserResponse from(WaitingUser waitingUser) {
        return new PosWaitingUserResponse(
                waitingUser.getId(),
                waitingUser.getStoreId(),
                waitingUser.getName(),
                waitingUser.getPhoneNumber(),
                waitingUser.getCount()
        );
    }
}
