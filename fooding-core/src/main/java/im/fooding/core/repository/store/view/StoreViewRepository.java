package im.fooding.core.repository.store.view;

import org.springframework.stereotype.Repository;

@Repository
public interface StoreViewRepository {

    long addViewAndGetCount(long storeId, long viewerId);

    long addUnknownViewAndGetCount(long storeId);
}
