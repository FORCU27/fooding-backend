package im.fooding.core.repository.store;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreSortType;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QStoreRepository {

    Page<Store> list(
            Pageable pageable,
            StoreSortType sortType,
            SortDirection sortDirection,
            boolean includeDeleted
    );

    List<Store> listByUserId(long userId);
}
