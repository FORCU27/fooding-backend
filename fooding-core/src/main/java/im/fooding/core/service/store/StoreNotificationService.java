package im.fooding.core.service.store;

import im.fooding.core.model.store.StoreNotification;
import im.fooding.core.repository.store.StoreNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreNotificationService {

    private final StoreNotificationRepository storeNotificationRepository;

    public Page<StoreNotification> list(Pageable pageable) {
        return storeNotificationRepository.list(pageable);
    }

    public Page<StoreNotification> list(Long storeId, Pageable pageable){
      return storeNotificationRepository.findByStoreIdOrderByCreatedAtDesc(storeId, pageable);
    }

    public Page<StoreNotification> listByCategory(Long storeId, String category, Pageable pageable) {
      return storeNotificationRepository.findByStoreIdAndCategoryOrderByCreatedAtDesc(storeId, category, pageable);
    }
}
