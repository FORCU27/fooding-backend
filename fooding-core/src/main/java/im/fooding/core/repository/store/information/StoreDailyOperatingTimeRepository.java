package im.fooding.core.repository.store.information;

import im.fooding.core.model.store.information.StoreDailyOperatingTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDailyOperatingTimeRepository extends JpaRepository<StoreDailyOperatingTime, Long> {
}
