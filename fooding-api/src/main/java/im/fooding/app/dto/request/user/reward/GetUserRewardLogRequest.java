package im.fooding.app.dto.request.user.reward;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.reward.RewardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetUserRewardLogRequest extends BasicSearch {
    
    @Schema( description = "리워드 로그 상태" )
    private RewardStatus status;
}
