package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.information.StoreDailyOperatingTime;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.repository.store.information.StoreDailyOperatingTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreDailyOperatingTimeService {
    private final StoreDailyOperatingTimeRepository repository;

    public void create(StoreOperatingHour storeOperatingHour, DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime, LocalTime breakStartTime, LocalTime breakEndTime) {
        StoreDailyOperatingTime storeDailyOperatingTime = StoreDailyOperatingTime.builder()
                .storeOperatingHour(storeOperatingHour)
                .dayOfWeek(dayOfWeek)
                .openTime(openTime)
                .closeTime(closeTime)
                .breakStartTime(breakStartTime)
                .breakEndTime(breakEndTime)
                .build();
        repository.save(storeDailyOperatingTime);
    }

    public void update(long id, DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime, LocalTime breakStartTime, LocalTime breakEndTime) {
        StoreDailyOperatingTime storeDailyOperatingTime = findById(id);
        storeDailyOperatingTime.update(dayOfWeek, openTime, closeTime, breakStartTime, breakEndTime);
    }

    public StoreDailyOperatingTime findById(long id) {
        return repository.findById(id).filter(it -> !it.isDeleted()).orElseThrow(() -> new ApiException(ErrorCode.STORE_OPERATING_HOUR_NOT_FOUND));
    }
}
