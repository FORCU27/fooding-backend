package im.fooding.app.service.job;

import im.fooding.app.dto.request.job.store.JobStoreCreateStatisticsRequest;
import im.fooding.app.service.common.store.PopularStoreCacheService;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.store.StoreStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobService {

    private final StoreService storeService;
    private final StoreStatisticsService storeStatisticsService;
    private final PopularStoreCacheService popularStoreCacheService;

    public void createStoreStatistics(JobStoreCreateStatisticsRequest request) {
        List<Store> allStore = storeService.findAll();

        for (Store store : allStore) {
            storeStatisticsService.upsert(
                    store.getId(),
                    request.getDate(),
                    getTotalSales(),
                    getTotalSalesChangeRate(),
                    getTotalVisitors(),
                    getVisitorChangeRate(),
                    getAnnualTargetSalesRate()
            );
        }
    }

    public void refreshPopularStoresCache() {
        popularStoreCacheService.refreshPopularStores();
    }

    private static int getTotalSales() {
        // todo: implement
        return 0;
    }

    private static double getTotalSalesChangeRate() {
        // todo: implement
        return 0;
    }

    private static int getTotalVisitors() {
        // todo: implement
        return 0;
    }

    private static double getVisitorChangeRate() {
        // todo: implement
        return 0;
    }

    private static double getAnnualTargetSalesRate() {
        // todo: implement
        return 0;
    }
}
