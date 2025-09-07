package im.fooding.core.repository.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import java.util.Optional;

public interface QWaitingSettingRepository {

    Optional<WaitingSetting> findActive(Store store);

    // 단일 리스트 조회: nullable 필터(waitingId, isActive)와 페이지네이션 지원
    org.springframework.data.domain.Page<WaitingSetting> list(Long waitingId, Boolean isActive, org.springframework.data.domain.Pageable pageable);
}
