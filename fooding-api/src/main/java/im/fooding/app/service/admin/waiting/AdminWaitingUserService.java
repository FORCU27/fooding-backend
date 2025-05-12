package im.fooding.app.service.admin.waiting;


import im.fooding.app.dto.request.admin.waiting.AdminWaitingUserCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingUserUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingUserResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.WaitingUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminWaitingUserService {

    private final WaitingUserService waitingUserService;
    private final StoreService storeService;

    @Transactional
    public void create(AdminWaitingUserCreateRequest request) {
        Store store = storeService.findById(request.storeId());
        waitingUserService.create(request.toWaitingUserCreateRequest(store));
    }

    public AdminWaitingUserResponse get(long id) {
        return AdminWaitingUserResponse.from(waitingUserService.get(id));
    }

    public PageResponse<AdminWaitingUserResponse> list(BasicSearch search) {
        Page<WaitingUser> waitingUsers = waitingUserService.list(search.getPageable());
        return PageResponse.of(waitingUsers.stream().map(AdminWaitingUserResponse::from).toList(), PageInfo.of(waitingUsers));
    }

    @Transactional
    public void update(long id, AdminWaitingUserUpdateRequest request) {
        Store store = storeService.findById(request.storeId());
        waitingUserService.update(request.toWaitingUserUpdateRequest(id, store));
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        waitingUserService.delete(id, deletedBy);
    }
}
