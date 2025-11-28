package im.fooding.realtime.app.repository.reward;

import im.fooding.core.model.reward.RewardStatus;
import im.fooding.realtime.app.domain.reward.RewardLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface DRewardLogRepository {
    Mono<Page<RewardLog>> list(String searchString, Pageable pageable, Long storeId, String phoneNumber, RewardStatus status);
    Mono<Long> listCount( String searchString, Long storeId, String phoneNumber, RewardStatus status );
}
