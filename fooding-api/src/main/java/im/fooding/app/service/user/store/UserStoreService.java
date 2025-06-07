package im.fooding.app.service.user.store;

import im.fooding.app.dto.request.user.store.UserSearchStoreRequest;
import im.fooding.app.dto.response.user.store.UserStoreListResponse;
import im.fooding.app.dto.response.user.store.UserStoreResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.WaitingSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStoreService {
    private final StoreService storeService;
    private final WaitingSettingService waitingSettingService;

    @Transactional(readOnly = true)
    public PageResponse<UserStoreListResponse> list(UserSearchStoreRequest request) {
        Page<Store> storePage = storeService.list(request.getPageable(), request.getSortType(), request.getSortDirection(), false);

        List<UserStoreListResponse> content = storePage.getContent().stream()
                .map(this::mapStoreToResponse)
                .toList();

        return PageResponse.of(content, PageInfo.of(storePage));
    }

    @Transactional
    public UserStoreResponse retrieve(Long id) {
        Store store = storeService.findById(id);
        storeService.increaseVisitCount(store);
        return UserStoreResponse.of(store, getEstimatedWaitingTime(store));
    }

    private Integer getEstimatedWaitingTime(Store store) {
        return waitingSettingService.findActiveSetting(store)
                .map(WaitingSetting::getEstimatedWaitingTimeMinutes)
                .orElse(null);
    }

    private UserStoreListResponse mapStoreToResponse(Store store) {
        //TODO: 이미지 추가
        return UserStoreListResponse.of(
                store,
                getEstimatedWaitingTime(store)
        );
    }
}
