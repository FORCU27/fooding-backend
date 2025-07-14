package im.fooding.app.service.app.store;

import im.fooding.app.dto.request.app.store.AppSearchStoreRequest;
import im.fooding.app.dto.response.app.store.AppStoreResponse;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<AppStoreResponse> list(long userId, AppSearchStoreRequest search) {
        return storeService.list(userId, search.toStoreFilter()).stream().map(AppStoreResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AppStoreResponse retrieve(long id, long userId) {
        storeMemberService.checkMember(id, userId);
        return AppStoreResponse.from(storeService.findById(id));
    }
}
