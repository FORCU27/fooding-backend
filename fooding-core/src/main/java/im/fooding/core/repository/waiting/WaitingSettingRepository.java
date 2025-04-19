package im.fooding.core.repository.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WaitingSettingRepository extends JpaRepository<WaitingSetting, Long> {

    @Query("SELECT ws FROM WaitingSetting ws WHERE ws.waiting.store = :store AND ws.isActive = true")
    Optional<WaitingSetting> findActive(@Param("store") Store store);
}
