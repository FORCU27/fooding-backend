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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppStoreService {
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;

    @Transactional(readOnly = true)
    public List<AppStoreResponse> list(long userId) {
        return storeService.list(userId).stream().map(AppStoreResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AppStoreResponse retrieve(long id, long userId) {
        storeMemberService.checkMember(id, userId);
        return AppStoreResponse.from(storeService.findById(id));
    }
}
