package im.fooding.core.repository.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface QWaitingSettingRepository {

    Optional<WaitingSetting> findActive(@Param("store") Store store);
}
