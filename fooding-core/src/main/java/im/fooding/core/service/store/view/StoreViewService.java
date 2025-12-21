package im.fooding.core.service.store.view;

import org.springframework.stereotype.Service;

@Service
public interface StoreViewService {

    long addViewAndGetCount(long storeId, long viewerId);

    long addUnknownViewAndGetCount(long storeId);
}
