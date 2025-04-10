package im.fooding.app.service.waiting;

import im.fooding.app.dto.request.waiting.WaitingListByStoreIdRequest;
import im.fooding.app.dto.response.waiting.WaitingResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.service.waiting.StoreWaitingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AppWaitingApplicationService {

    private final StoreWaitingService storeWaitingService;

    public PageResponse<WaitingResponse> listByStoreIdAndStatus(long storeId, WaitingListByStoreIdRequest request) {
        Page<StoreWaiting> storeWaitings = storeWaitingService.getAllByStoreIdAndStatus(storeId, request.status(), request.pageable());

        List<WaitingResponse> list = storeWaitings.getContent()
                .stream()
                .map(WaitingResponse::from)
                .toList();

        return PageResponse.of(list, PageInfo.of(storeWaitings));
    }
}
