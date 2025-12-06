package im.fooding.app.service.common.store;

import im.fooding.app.dto.response.common.PopularStoreCache;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.store.StoreStatus;
import im.fooding.core.service.store.StoreService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PopularStoreCacheService {

    private final StoreService storeService;

    private static final String CACHE_NAME = "PopularStoreList";
    private static final String CACHE_KEY = "'topByReview'";
    private static final String CACHE_MANAGER = "contentCacheManager";

    @Cacheable(
            value = CACHE_NAME,
            key = CACHE_KEY,
            cacheManager = CACHE_MANAGER
    )
    @Transactional(readOnly = true)
    public List<PopularStoreCache> getPopularStores() {
        return fetchPopularStoresFromDB();
    }

    @CachePut(
            value = CACHE_NAME,
            key = CACHE_KEY,
            cacheManager = CACHE_MANAGER
    )
    @Transactional(readOnly = true)
    public List<PopularStoreCache> refreshPopularStores() {
        return fetchPopularStoresFromDB();
    }

    private List<PopularStoreCache> fetchPopularStoresFromDB() {
        Set<StoreStatus> userVisibleStatuses = EnumSet.of(
                StoreStatus.APPROVED
        );

        Page<Store> stores = storeService.list(Pageable.ofSize(10), StoreSortType.REVIEW, SortDirection.DESCENDING, null, null, null, null, false, userVisibleStatuses, null);

        return stores.map(PopularStoreCache::from).toList();
    }
}
