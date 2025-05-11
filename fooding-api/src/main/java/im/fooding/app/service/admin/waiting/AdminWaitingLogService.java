package im.fooding.app.service.admin.waiting;


import im.fooding.app.dto.request.admin.waiting.AdminWaitingLogCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingLogUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingLogResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminWaitingLogService {

    private final WaitingLogService waitingLogService;
    private final StoreWaitingService storeWaitingService;

    @Transactional
    public void create(AdminWaitingLogCreateRequest request) {
        StoreWaiting storeWaiting = storeWaitingService.get(request.storeWaitingId());

        waitingLogService.create(request.toWaitingLogCreateRequest(storeWaiting));
    }

    public AdminWaitingLogResponse get(long id) {
        return AdminWaitingLogResponse.from(waitingLogService.get(id));
    }

    public PageResponse<AdminWaitingLogResponse> list(BasicSearch search) {
        Page<WaitingLog> waitingLogs = waitingLogService.list(search.getPageable());
        return PageResponse.of(waitingLogs.stream().map(AdminWaitingLogResponse::from).toList(), PageInfo.of(waitingLogs));
    }

    @Transactional
    public void update(long id, AdminWaitingLogUpdateRequest request) {
        StoreWaiting storeWaiting = storeWaitingService.get(request.storeWaitingId());

        waitingLogService.update(request.toWaitingLogUpdateRequest(id, storeWaiting));
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        waitingLogService.delete(id, deletedBy);
    }
}
