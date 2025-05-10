package im.fooding.app.dto.request.user.reward;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRewardPointRequest {
    @NotNull
    @Schema( description = "전화번호" )
    private String phoneNumber;

    @NotNull
    @Schema( description = "가게 ID" )
    private Long storeId;

    @NotNull
    @Schema( description = "포인트" )
    private int point;

    @NotNull
    @Schema( description = "리워드 타입" )
    private RewardType type;

    @NotNull
    @Schema( description = "리워드 채널" )
    private RewardChannel channel;
}
