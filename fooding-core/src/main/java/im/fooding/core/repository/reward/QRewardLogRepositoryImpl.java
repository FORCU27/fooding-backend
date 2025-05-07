package im.fooding.core.repository.reward;

import im.fooding.core.model.reward.RewardLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QRewardLogRepositoryImpl implements QRewardLogRepository {
    @Override
    public Page<RewardLog> list(String searchString, Pageable pageable, Long storeId, String phoneNumber) {
        return null;
    }
}
