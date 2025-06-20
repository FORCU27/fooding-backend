package im.fooding.core.repository.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {

    boolean existsByStore(Store store);

    Page<Waiting> findAllByDeletedFalse(Pageable pageable);

    Page<Waiting> findAllByStatusAndDeletedFalse(WaitingStatus status, Pageable pageable);

    Optional<Waiting> findByStore(Store store);
}
