package im.fooding.core.repository.store.information;

import im.fooding.core.model.store.information.StoreOperatingHour;

import java.time.DayOfWeek;
import java.util.List;

public interface QStoreOperatingHourRepository {
    List<StoreOperatingHour> findByIdsInOperatingTime(List<Long> ids, DayOfWeek week);
}
