package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StoreStatistics;
import im.fooding.core.repository.store.StoreStatisticsRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StoreStatisticsService {

    private StoreStatisticsRepository storeStatisticsRepository;

    public StoreStatistics get(long storeId, LocalDate date) {
        return storeStatisticsRepository.findByStoreIdAndDate(storeId, date)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_STATISTICS_NOT_FOUND));
    }
}
