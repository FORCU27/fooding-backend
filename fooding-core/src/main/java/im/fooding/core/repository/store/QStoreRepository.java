package im.fooding.core.repository.store;

import im.fooding.core.dto.request.store.StoreFilter;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.store.StoreStatus;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QStoreRepository {

    Page<Store> list(
            Pageable pageable,
            StoreSortType sortType,
            SortDirection sortDirection,
            List<String> regionIds,
            StoreCategory category,
            boolean includeDeleted,
            Set<StoreStatus> statuses,
            String searchString
    );

    List<Store> listByUserId(long userId, StoreFilter filter);

    Optional<Store> retrieve(long storeId, Set<StoreStatus> statuses);

    List<Store> list(List<Long> ids);
}
