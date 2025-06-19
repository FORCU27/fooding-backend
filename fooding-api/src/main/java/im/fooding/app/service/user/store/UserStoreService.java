package im.fooding.app.service.user.store;

import im.fooding.app.dto.request.user.store.UserSearchStoreRequest;
import im.fooding.app.dto.response.user.store.UserStoreListResponse;
import im.fooding.app.dto.response.user.store.UserStoreResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.service.store.StoreImageService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.WaitingSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserStoreService {
    private final StoreService storeService;
    private final WaitingSettingService waitingSettingService;

    @Transactional(readOnly = true)
    public PageResponse<UserStoreListResponse> list(UserSearchStoreRequest request) {
        Page<Store> stores = storeService.list(request.getPageable(), request.getSortType(), request.getSortDirection(), false);
        return PageResponse.of(
                stores.getContent().stream().map(store -> UserStoreListResponse.of(store, null)).toList(),
                PageInfo.of(stores)
        );
    }

    @Transactional
    public UserStoreResponse retrieve(Long id) {
        Store store = storeService.retrieve(id);
        storeService.increaseVisitCount(store);
        return UserStoreResponse.of(store, null);
    }

    private Integer getEstimatedWaitingTime(Store store) {
        //TODO: n + 1 이슈있음 예상 웨이팅 시간 어떻게할지
        return waitingSettingService.findActiveSetting(store)
                .map(WaitingSetting::getEstimatedWaitingTimeMinutes)
                .orElse(null);
    }
}
