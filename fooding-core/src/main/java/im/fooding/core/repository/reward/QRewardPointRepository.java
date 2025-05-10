package im.fooding.core.repository.reward;

import im.fooding.core.model.reward.RewardPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QRewardPointRepository {
    Page<RewardPoint> list(String searchString, Pageable pageable, Long storeId, String phoneNumber );
}
