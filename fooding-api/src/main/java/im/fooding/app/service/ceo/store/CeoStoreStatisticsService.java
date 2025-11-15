package im.fooding.app.service.ceo.store;

import im.fooding.app.dto.request.ceo.store.CeoStoreStatisticsRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreStatisticsResponse;
import im.fooding.core.model.store.StoreStatistics;
import im.fooding.core.service.store.StoreStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CeoStoreStatisticsService {

    private final StoreStatisticsService storeStatisticsService;

    public CeoStoreStatisticsResponse retrieve(long storeId, CeoStoreStatisticsRequest request) {
        StoreStatistics storeStatistics = storeStatisticsService.get(storeId, request.getDate());

        return CeoStoreStatisticsResponse.builder()
                .totalSales(storeStatistics.getTotalSales())
                .totalSalesChangeRate(doubleToInt(storeStatistics.getAnnualTargetSalesRate()))
                .totalVisitors(storeStatistics.getTotalVisitors())
                .visitorChangeRate(doubleToInt(storeStatistics.getVisitorChangeRate()))
                .annualTargetSalesRate(doubleToInt((storeStatistics.getAnnualTargetSalesRate())))
                .currentWaitingCount(4)
                .expectedWaitingTime(25)
                .lastEntranceMinutesAgo(3)
                .build();
    }

    private int doubleToInt(double value) {
        return (int) value * 100;
    }
}
