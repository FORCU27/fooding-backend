package im.fooding.core.service.store.view;

import im.fooding.core.repository.store.view.StoreViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreViewService {

    private final StoreViewRepository storeViewRepository;

    public long addViewAndGetCount(long storeId, long viewerId) {
        return storeViewRepository.addViewAndGetCount(storeId, viewerId);
    }

    public long addUnknownViewAndGetCount(long storeId) {
        return storeViewRepository.addUnknownViewAndGetCount(storeId);
    }
}
