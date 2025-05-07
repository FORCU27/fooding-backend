package im.fooding.core.repository.reward;

import im.fooding.core.model.reward.RewardPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QRewardPointRepositoryImpl implements QRewardPointRepository{
    @Override
    public Page<RewardPoint> list(String searchString, Pageable pageable, Long storeId, String phoneNumber) {
        return null;
    }
}
