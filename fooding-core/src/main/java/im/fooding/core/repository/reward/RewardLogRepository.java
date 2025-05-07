package im.fooding.core.repository.reward;

import im.fooding.core.model.reward.RewardLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardLogRepository extends JpaRepository<RewardLog, Long>, QRewardLogRepository {
}
