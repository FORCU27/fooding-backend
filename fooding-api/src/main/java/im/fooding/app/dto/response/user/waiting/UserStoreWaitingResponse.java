package im.fooding.app.dto.response.user.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class UserStoreWaitingResponse {

    @Schema(description = "유저 id", example = "1")
    Long userId;

    @Schema(description = "웨이팅 유저 id", example = "1")
    Long waitingUserId;

    @Schema(description = "가게 id", example = "1")
    Long storeId;

    @Schema(description = "등록 방법(IN_PERSON, ONLINE)", example = "IN_PERSON")
    String channel;

    @Schema(description = "유아용 의자 개수", example = "1")
    Integer infantChairCount;

    @Schema(description = "유아 수", example = "1")
    Integer infantCount;

    @Schema(description = "성인 수", example = "1")
    Integer adultCount;

    @Schema(description = "메모", example = "메모 내용입니다.")
    String memo;

    public static UserStoreWaitingResponse from(StoreWaiting waiting) {
        Long userId = null;
        if (waiting.getUser() != null) {
            userId = waiting.getUser().getId();
        }
        Long waitingUserId = null;
        if (waiting.getWaitingUser() != null) {
            waitingUserId = waiting.getWaitingUser().getId();
        }
        return new UserStoreWaitingResponse(
                userId,
                waitingUserId,
                waiting.getStoreId(),
                waiting.getChannel().name(),
                waiting.getInfantChairCount(),
                waiting.getInfantCount(),
                waiting.getAdultCount(),
                waiting.getMemo()
        );
    }
}
