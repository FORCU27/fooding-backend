package im.fooding.app.dto.response.admin.reward;

import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminRewardLogResponse {
    private Long id;
    private Long storeId;
    private String storeName;
    private String phoneNumber;
    private Integer point;
    private RewardStatus status;
    private RewardType type;
    private RewardChannel channel;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
