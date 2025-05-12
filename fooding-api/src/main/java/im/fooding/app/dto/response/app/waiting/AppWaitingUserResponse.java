package im.fooding.app.dto.response.app.waiting;

import im.fooding.core.model.waiting.WaitingUser;
import io.swagger.v3.oas.annotations.media.Schema;

public record AppWaitingUserResponse(
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
    public static AppWaitingUserResponse from(WaitingUser waitingUser) {
        return new AppWaitingUserResponse(
                waitingUser.getId(),
                waitingUser.getStoreId(),
                waitingUser.getName(),
                waitingUser.getPhoneNumber(),
                waitingUser.getCount()
        );
    }
}
