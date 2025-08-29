package im.fooding.app.dto.request.admin.reward;

import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateRewardLogRequest {
    private Long storeId;
    
    private String phoneNumber;
    
    @Positive(message = "포인트는 양수여야 합니다")
    private Integer point;
    
    private RewardStatus status;
    
    private RewardType type;
    
    private RewardChannel channel;
    
    private String memo;
}
