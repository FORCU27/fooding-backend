package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.model.store.information.StoreRegularHolidayType;
import im.fooding.core.repository.store.information.StoreOperatingHourRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreOperatingHourService {
    private final StoreOperatingHourRepository repository;

    public StoreOperatingHour create(Store store, boolean hasHoliday, StoreRegularHolidayType regularHolidayType, DayOfWeek regularHoliday, String closedNationalHolidays, String customHolidays, String operatingNotes) {
        checkDuplicate(store.getId());
        StoreOperatingHour storeOperatingHour = StoreOperatingHour.builder()
                .store(store)
                .hasHoliday(hasHoliday)
                .regularHolidayType(regularHolidayType)
                .regularHoliday(regularHoliday)
                .closedNationalHolidays(closedNationalHolidays)
                .customHolidays(customHolidays)
                .operatingNotes(operatingNotes)
                .build();
        return repository.save(storeOperatingHour);
    }

    public void update(long id, boolean hasHoliday, StoreRegularHolidayType regularHolidayType, DayOfWeek regularHoliday, String closedNationalHolidays, String customHolidays, String operatingNotes) {
        StoreOperatingHour storeOperatingHour = findById(id);
        storeOperatingHour.update(hasHoliday, regularHolidayType, regularHoliday, closedNationalHolidays, customHolidays, operatingNotes);
    }

    public StoreOperatingHour findById(long id) {
        return repository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_OPERATING_HOUR_NOT_FOUND));
    }

    public void delete(Long id, long deletedBy) {
        StoreOperatingHour storeOperatingHour = findById(id);
        storeOperatingHour.delete(deletedBy);
    }

    public StoreOperatingHour findByStoreId(long storeId) {
        return repository.findByStoreIdAndDeletedIsFalse(storeId).orElse(null);
    }

    public List<StoreOperatingHour> findByIdsInOperatingTime(List<Long> storeIds, DayOfWeek week) {
        return repository.findByIdsInOperatingTime(storeIds, week);
    }

    private void checkDuplicate(long storeId) {
        if (repository.existsByStoreIdAndDeletedIsFalse(storeId)) {
            throw new ApiException(ErrorCode.STORE_OPERATING_HOUR_DUPLICATED);
        }
    }
}
