package im.fooding.app.service.job;

import im.fooding.app.dto.request.job.store.JobStoreCreateStatisticsRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.store.StoreStatisticsService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class JobService {

    private final StoreService storeService;
    private final StoreStatisticsService storeStatisticsService;

    @Transactional
    public void createStoreStatistics(JobStoreCreateStatisticsRequest request) {
        List<Store> allStore = storeService.findAll();

        allStore.forEach(store ->
                {
                    try {
                        createStoreStatistics(store.getId(), request.getDate());
                    } catch (Exception e) {
                        log.error("통계 생성에 실패했습니다. (storeId={} on date={})", store.getId(), request.getDate());
                    }
                }
        );
    }

    @Transactional
    public void createStoreStatistics(long storeId, LocalDate date) {
        try {
            storeStatisticsService.update(
                    storeId,
                    date,
                    getTotalSales(),
                    getTotalSalesChangeRate(),
                    getTotalVisitors(),
                    getVisitorChangeRate(),
                    getAnnualTargetSalesRate()
            );
        } catch (ApiException e) {
            if (e.getErrorCode() == ErrorCode.STORE_STATISTICS_NOT_FOUND) {
                storeStatisticsService.create(
                        storeId,
                        date,
                        getTotalSales(),
                        getTotalSalesChangeRate(),
                        getTotalVisitors(),
                        getVisitorChangeRate(),
                        getAnnualTargetSalesRate());
            } else {
                throw e;
            }
        }
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
