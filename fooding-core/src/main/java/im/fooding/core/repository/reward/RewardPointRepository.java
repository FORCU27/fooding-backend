package im.fooding.core.repository.reward;

import im.fooding.core.model.reward.RewardPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardPointRepository extends JpaRepository<RewardPoint, Long>, QRewardPointRepository {
    RewardPoint findByPhoneNumberAndStoreId( String phoneNumber, Long storeId );
}
