package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.information.StoreDailyBreakTime;
import im.fooding.core.model.store.information.StoreDailyOperatingTime;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.repository.store.information.StoreDailyBreakTimeRepository;
import im.fooding.core.repository.store.information.StoreDailyOperatingTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreDailyBreakTimeService {
    private final StoreDailyBreakTimeRepository repository;

    public void create(StoreOperatingHour storeOperatingHour, DayOfWeek dayOfWeek, LocalTime breakStartTime, LocalTime breakEndTime) {
        StoreDailyBreakTime storeDailyBreakTime = StoreDailyBreakTime.builder()
                .storeOperatingHour(storeOperatingHour)
                .dayOfWeek(dayOfWeek)
                .breakStartTime(breakStartTime)
                .breakEndTime(breakEndTime)
                .build();
        repository.save(storeDailyBreakTime);
    }

    public void initialize(StoreOperatingHour storeOperatingHour) {
        List<DayOfWeek> weeks = Arrays.asList(DayOfWeek.values());
        List<StoreDailyBreakTime> entities = new ArrayList<>();
        weeks.forEach(week -> {
            entities.add(
                    StoreDailyBreakTime.builder()
                            .storeOperatingHour(storeOperatingHour)
                            .dayOfWeek(week)
                            .breakStartTime(null)
                            .breakEndTime(null)
                            .build()
            );
        });
        repository.saveAll(entities);
    }

    public void update(long id, DayOfWeek dayOfWeek, LocalTime breakStartTime, LocalTime breakEndTime) {
        StoreDailyBreakTime storeDailyBreakTime = findById(id);
        storeDailyBreakTime.update(dayOfWeek, breakStartTime, breakEndTime);
    }

    public StoreDailyBreakTime findById(long id) {
        return repository.findById(id).filter(it -> !it.isDeleted()).orElseThrow(() -> new ApiException(ErrorCode.STORE_OPERATING_HOUR_NOT_FOUND));
    }

}
