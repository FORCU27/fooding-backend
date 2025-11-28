package im.fooding.realtime.app.repository.reward;

import im.fooding.realtime.app.domain.reward.RewardPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface DRewardPointRepository {
    Mono<Page<RewardPoint>> list(String searchString, Pageable pageable, Long storeId, String phoneNumber );
}
