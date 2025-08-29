package im.fooding.app.dto.request.admin.reward;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSearchRewardLogRequest extends BasicSearch {
    private Long storeId;
    private String phoneNumber;
    private RewardStatus status;
    private RewardType type;
    private RewardChannel channel;
    private String memo;
}
