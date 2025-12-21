package im.fooding.core.service.store.popular;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.store.StoreStatus;
import im.fooding.core.model.store.popular.PopularStore;
import im.fooding.core.repository.store.StoreRepository;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PopularStoreService {

    private final StoreRepository storeRepository;

    private static final String CACHE_NAME = "PopularStoreList";
    private static final String CACHE_KEY = "'topByReview'";
    private static final String CACHE_MANAGER = "contentCacheManager";

    @Cacheable(
            value = CACHE_NAME,
            key = CACHE_KEY,
            cacheManager = CACHE_MANAGER
    )
    @Transactional(readOnly = true)
    public List<PopularStore> getPopularStores() {
        return fetchPopularStoresFromDB();
    }

    @CachePut(
            value = CACHE_NAME,
            key = CACHE_KEY,
            cacheManager = CACHE_MANAGER
    )
    @Transactional(readOnly = true)
    public List<PopularStore> refreshPopularStores() {
        return fetchPopularStoresFromDB();
    }

    private List<PopularStore> fetchPopularStoresFromDB() {
        Set<StoreStatus> userVisibleStatuses = EnumSet.of(
                StoreStatus.APPROVED
        );

        Page<Store> stores = storeRepository.list(Pageable.ofSize(10), StoreSortType.REVIEW, SortDirection.DESCENDING, null, null, null, null, false, userVisibleStatuses, null);

        return stores.map(PopularStore::from).toList();
    }
}
