package im.fooding.realtime.app.repository.reward;

import im.fooding.realtime.app.domain.reward.RewardLog;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RewardLogRepository extends R2dbcRepository<RewardLog, Long>, DRewardLogRepository {

}
