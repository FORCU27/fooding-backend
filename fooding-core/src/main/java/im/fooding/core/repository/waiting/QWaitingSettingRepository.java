package im.fooding.core.repository.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import java.util.Optional;

public interface QWaitingSettingRepository {

    Optional<WaitingSetting> findActive(Store store);
}
