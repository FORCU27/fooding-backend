package im.fooding.app.service.waiting;

import im.fooding.app.dto.request.waiting.WaitingListRequest;
import im.fooding.app.dto.response.waiting.WaitingResponse;
import im.fooding.app.service.user.notification.UserNotificationApplicationService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import im.fooding.core.model.waiting.*;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingService;
import im.fooding.core.service.waiting.WaitingSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PosWaitingService {

    private final UserNotificationApplicationService userNotificationApplicationService;
    private final WaitingService waitingService;
    private final StoreWaitingService storeWaitingService;
    private final WaitingSettingService waitingSettingService;

    public PageResponse<WaitingResponse> list(long id, WaitingListRequest request) {

        Waiting waiting = waitingService.getById(id);

        StoreWaitingFilter storeWaitingFilter = StoreWaitingFilter.builder()
                .storeId(waiting.getStore().getId())
                .status(StoreWaitingStatus.of(request.status()))
                .build();
        Page<StoreWaiting> storeWaitings = storeWaitingService.list(storeWaitingFilter, request.pageable());

        List<WaitingResponse> list = storeWaitings.getContent()
                .stream()
                .map(WaitingResponse::from)
                .toList();

        return PageResponse.of(list, PageInfo.of(storeWaitings));
    }

    @Transactional
    public void seat(long requestId) {
        storeWaitingService.seat(requestId);
    }

    @Transactional
    public void cancel(long requestId, String reason) {
        StoreWaiting canceledWaiting = storeWaitingService.cancel(requestId);
        userNotificationApplicationService.sendWaitingCancelMessage(canceledWaiting.getStoreName(), reason);
    }

    @Transactional
    public void call(long requestId) {
        StoreWaiting storeWaiting = storeWaitingService.call(requestId);

        WaitingSetting waitingSetting = waitingSettingService.getActiveSetting(storeWaiting.getStore());

        userNotificationApplicationService.sendWaitingCallMessage(
                storeWaiting.getStoreName(),
                storeWaiting.getCallNumber(),
                waitingSetting.getEntryTimeLimitMinutes()
        );
    }
}
