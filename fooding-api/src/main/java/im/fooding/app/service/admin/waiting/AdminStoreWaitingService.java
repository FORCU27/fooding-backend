package im.fooding.app.service.admin.waiting;


import im.fooding.app.dto.request.admin.waiting.AdminStoreWaitingCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminStoreWaitingUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminStoreWaitingResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminStoreWaitingService {

    private final StoreWaitingService storeWaitingService;
    private final WaitingUserService waitingUserService;
    private final StoreService storeService;

    @Transactional
    public AdminStoreWaitingResponse create(AdminStoreWaitingCreateRequest request) {
        WaitingUser user = waitingUserService.get(request.userId());
        Store store = storeService.findById(request.storeId());

        StoreWaiting waiting = storeWaitingService.create(request.toStoreWaitingCreateRequest(user, store));

        return AdminStoreWaitingResponse.from(waiting);
    }

    public AdminStoreWaitingResponse get(long id) {
        return AdminStoreWaitingResponse.from(storeWaitingService.get(id));
    }

    public PageResponse<AdminStoreWaitingResponse> list(BasicSearch search) {
        Page<StoreWaiting> storeWaitings = storeWaitingService.list(search.getPageable());
        return PageResponse.of(storeWaitings.stream().map(AdminStoreWaitingResponse::from).toList(), PageInfo.of(storeWaitings));
    }

    @Transactional
    public AdminStoreWaitingResponse update(long id, AdminStoreWaitingUpdateRequest request) {
        Store store = storeService.findById(request.storeId());
        WaitingUser user = waitingUserService.get(request.userId());

        return AdminStoreWaitingResponse.from(storeWaitingService.update(request.toStoreWaitingUpdateRequest(id, user, store)));
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        storeWaitingService.delete(id, deletedBy);
    }
}
