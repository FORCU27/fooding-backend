package im.fooding.core.repository.waiting;

import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.waiting.WaitingSetting;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingSettingRepository extends JpaRepository<WaitingSetting, Long>, QWaitingSettingRepository {

    boolean existsByStoreServiceAndIsActiveTrueAndDeletedFalse(StoreService waiting);

    Page<WaitingSetting> findAllByDeletedFalse(Pageable pageable);

    Optional<WaitingSetting> findByStoreService(StoreService storeService);
}
