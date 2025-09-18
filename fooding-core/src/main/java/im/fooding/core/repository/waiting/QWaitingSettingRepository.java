package im.fooding.core.repository.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QWaitingSettingRepository {

    Optional<WaitingSetting> findActive(Store store);

    // 단일 리스트 조회: nullable 필터(waitingId, isActive)와 페이지네이션 지원
    Page<WaitingSetting> list(Long storeServiceId, Boolean isActive, Pageable pageable);

    Page<WaitingSetting> list(WaitingSettingFilter filter, Pageable pageable);
}
