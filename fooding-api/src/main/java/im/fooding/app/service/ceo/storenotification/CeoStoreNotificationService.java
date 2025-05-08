package im.fooding.app.service.ceo.storenotification;

import im.fooding.app.dto.response.ceo.storenotification.CeoStoreNotificationResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.StoreNotification;
import im.fooding.core.service.store.StoreNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CeoStoreNotificationService {
    private final StoreNotificationService storeNotificationService;

    public PageResponse<CeoStoreNotificationResponse> getNotifications(Long storeId, String category, Pageable pageable) {
      Page<StoreNotification> page;

      if (category == null || category.trim().isEmpty()) {
        page = storeNotificationService.list(storeId, pageable);
      } else {
        page = storeNotificationService.listByCategory(storeId, category, pageable);
      }

      List<CeoStoreNotificationResponse> content = page.map(CeoStoreNotificationResponse::from).getContent();
      return PageResponse.of(content, PageInfo.of(page));
    }
}
