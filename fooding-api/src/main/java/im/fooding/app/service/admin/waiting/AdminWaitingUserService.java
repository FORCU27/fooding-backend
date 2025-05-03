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
    public AdminWaitingUserResponse create(AdminWaitingUserCreateRequest request) {
        Store store = storeService.findById(request.storeId());
        WaitingUser waitingUser = waitingUserService.create(request.toWaitingUserCreateRequest(store));
        return AdminWaitingUserResponse.from(waitingUser);
    }

    public AdminWaitingUserResponse get(long userId) {
        return AdminWaitingUserResponse.from(waitingUserService.get(userId));
    }

    public PageResponse<AdminWaitingUserResponse> getList(BasicSearch search) {
        Page<WaitingUser> waitingUsers = waitingUserService.getList(search.getPageable());
        return PageResponse.of(waitingUsers.stream().map(AdminWaitingUserResponse::from).toList(), PageInfo.of(waitingUsers));
    }

    @Transactional
    public AdminWaitingUserResponse update(long userId, AdminWaitingUserUpdateRequest request) {
        Store store = storeService.findById(request.storeId());
        return AdminWaitingUserResponse.from(waitingUserService.update(request.toWaitingUserUpdateRequest(userId, store)));
    }

    @Transactional
    public void delete(long userId, long deletedBy) {
        waitingUserService.delete(userId, deletedBy);
    }
}
