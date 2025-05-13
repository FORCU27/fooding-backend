package im.fooding.app.service.app.store;

import im.fooding.app.dto.response.app.store.AppStoreResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AppStoreService {

    private final StoreMemberService storeMemberService;
    private final StoreService storeService;

    // TODO: 유저, 권한 개발 후 자신이 접근할 수 있는 Store 만 조회할 수 있도록 수정한다
    public PageResponse<AppStoreResponse> getMyStores(Long userId, BasicSearch basicSearch) {
        Page<AppStoreResponse> storeResponsePage = storeService.list(basicSearch.getPageable())
                .map(AppStoreResponse::from);

        return PageResponse.of(storeResponsePage.toList(), PageInfo.of(storeResponsePage));
//        Page<AppStoreResponse> storeResponsePage = storeMemberService.getStores(userId, basicSearch.getPageable())
//                .map(AppStoreResponse::from);
//
//        return PageResponse.of(storeResponsePage.toList(), PageInfo.of(storeResponsePage));
    }
}
