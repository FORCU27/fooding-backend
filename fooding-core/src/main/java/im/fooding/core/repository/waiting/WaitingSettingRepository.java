package im.fooding.core.repository.waiting;

import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingSettingRepository extends JpaRepository<WaitingSetting, Long>, QWaitingSettingRepository {

    boolean existsByWaitingAndIsActiveTrueAndDeletedFalse(Waiting waiting);

    Page<WaitingSetting> findAllByDeletedFalse(Pageable pageable);
}
