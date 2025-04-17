package im.fooding.core.repository.waiting;

import im.fooding.core.model.waiting.WaitingSetting;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingSettingRepository extends JpaRepository<WaitingSetting, Long> {

    Optional<WaitingSetting> findByIsActiveIsTrue();
}
