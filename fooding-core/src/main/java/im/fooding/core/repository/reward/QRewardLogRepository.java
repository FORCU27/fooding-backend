package im.fooding.core.repository.reward;

import im.fooding.core.model.reward.RewardLog;
import im.fooding.core.model.reward.RewardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QRewardLogRepository {
    Page<RewardLog> list(String searchString, Pageable pageable, Long storeId, String phoneNumber, RewardStatus status);
    Long listCount(String searchString, Long storeId, String phoneNumber, RewardStatus status);
}
