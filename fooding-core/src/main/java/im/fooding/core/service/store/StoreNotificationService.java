package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
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

    @Transactional
    public Long create(Store store, String title, String content, String category, String linkUrl) {
        StoreNotification newStoreNotification = StoreNotification.builder()
                .store(store)
                .title(title)
                .content(content)
                .category(category)
                .linkUrl(linkUrl)
                .build();

        return storeNotificationRepository.save(newStoreNotification).getId();
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        StoreNotification storeNotification = get(id);

        storeNotification.delete(deletedBy);
    }

    private StoreNotification get(long id) {
        return storeNotificationRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_NOTIFICATION_NOT_FOUND));
    }
}
