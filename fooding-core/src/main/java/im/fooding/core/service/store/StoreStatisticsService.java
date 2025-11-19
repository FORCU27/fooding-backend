package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StoreStatistics;
import im.fooding.core.repository.store.StoreStatisticsRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreStatisticsService {

    private final StoreStatisticsRepository storeStatisticsRepository;

    public StoreStatistics get(long storeId, LocalDate date) {
        return storeStatisticsRepository.findByStoreIdAndDate(storeId, date)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_STATISTICS_NOT_FOUND));
    }

    public void create(
            long storeId,
            LocalDate date,
            int totalSales,
            double totalSalesChangeRate,
            int totalVisitors,
            double visitorChangeRate,
            double annualTargetSalesRate
    ) {
        StoreStatistics storeStatistics = StoreStatistics.builder()
                .storeId(storeId)
                .date(date)
                .totalSales(totalSales)
                .totalSalesChangeRate(totalSalesChangeRate)
                .totalVisitors(totalVisitors)
                .visitorChangeRate(visitorChangeRate)
                .annualTargetSalesRate(annualTargetSalesRate)
                .build();

        storeStatisticsRepository.save(storeStatistics);
    }

    public void update(
            long storeId,
            LocalDate date,
            int totalSales,
            double totalSalesChangeRate,
            int totalVisitors,
            double visitorChangeRate,
            double annualTargetSalesRate
    ) {
        StoreStatistics storeStatistics = get(storeId, date);

        storeStatistics.update(
                totalSales,
                totalSalesChangeRate,
                totalVisitors,
                visitorChangeRate,
                annualTargetSalesRate
        );
    }
}
