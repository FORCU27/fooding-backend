package im.fooding.app.service.admin.waiting;


import im.fooding.app.dto.request.admin.waiting.AdminWaitingCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingUpdateRequest;
import im.fooding.app.dto.response.waiting.WaitingResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.service.waiting.WaitingService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminWaitingService {

    private final WaitingService waitingService;
    private final StoreService storeService;

    @Transactional
    public WaitingResponse createWaiting(AdminWaitingCreateRequest request) {
        Store store = storeService.findById(request.storeId());
        WaitingStatus status = WaitingStatus.of(request.status());

        Waiting waiting = waitingService.create(store, status);

        return WaitingResponse.from(waiting);
    }

    public WaitingResponse getWaiting(long id) {
        return WaitingResponse.from(waitingService.getById(id));
    }

    public PageResponse<WaitingResponse> getWaitingList(BasicSearch search) {
        Page<Waiting> waitings = waitingService.getList(search.getPageable());
        return PageResponse.of(waitings.stream().map(WaitingResponse::from).toList(), PageInfo.of(waitings));
    }

    @Transactional
    public WaitingResponse updateWaiting(long id, AdminWaitingUpdateRequest request) {
        Store store = storeService.findById(request.storeId());
        WaitingStatus status = WaitingStatus.of(request.status());

        return WaitingResponse.from(waitingService.update(id, store, status));
    }

    @Transactional
    public void deleteWaiting(long id, long deletedBy) {
        waitingService.delete(id, deletedBy);
    }
}
