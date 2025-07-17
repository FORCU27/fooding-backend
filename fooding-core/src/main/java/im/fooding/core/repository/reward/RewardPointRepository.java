package im.fooding.core.repository.reward;

import im.fooding.core.model.reward.RewardPoint;
import im.fooding.core.model.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardPointRepository extends JpaRepository<RewardPoint, Long>, QRewardPointRepository {
    RewardPoint findByPhoneNumberAndStoreId(String phoneNumber, Long storeId);

    List<RewardPoint> user(User user);

    @EntityGraph(attributePaths = {"store", "user"})
    RewardPoint findByUserIdAndStoreIdAndDeletedIsFalse(long userId, long storeId);
}
