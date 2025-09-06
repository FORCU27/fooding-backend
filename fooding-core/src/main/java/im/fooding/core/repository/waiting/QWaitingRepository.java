package im.fooding.core.repository.waiting;

import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QWaitingRepository {
    Page<Waiting> list(Long storeId, WaitingStatus status, Pageable pageable);
}

