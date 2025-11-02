package im.fooding.core.repository.reward;

import im.fooding.core.model.reward.RewardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long>, QRewardHistoryRepository {

}
