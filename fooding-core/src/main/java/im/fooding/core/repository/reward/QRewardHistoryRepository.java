package im.fooding.core.repository.reward;

import im.fooding.core.model.reward.RewardHistory;

import java.util.List;

public interface QRewardHistoryRepository {
    List<RewardHistory> list( Long storeId, String phoneNumber, Boolean isCoupon );
}
