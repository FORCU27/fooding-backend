package im.fooding.core.repository.waiting;

import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import im.fooding.core.model.waiting.StoreWaiting;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QStoreWaitingRepository {

    Page<StoreWaiting> findAllWithFilterAndDeletedFalse(StoreWaitingFilter filter, Pageable pageable);

    long countCreatedOnAndDeletedFalse(LocalDate date);
}
