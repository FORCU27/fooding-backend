package im.fooding.app.dto.response.user.reward;

import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRewardLogResponse {
    @Schema( description = "Reward Log ID" )
    private Long id;

    @Schema( description = "가게 이름" )
    private String storeName;

    @Schema( description = "전화번호" )
    private String phoneNumber;

    @Schema( description = "기록 포인트" )
    private int point;

    @Schema( description = "리워드 상태" )
    private RewardStatus status;

    @Schema( description = "리워드 타입" )
    private RewardType type;

    @Schema( description = "리워드 채널" )
    private RewardChannel channel;
}
