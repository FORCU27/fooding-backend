package im.fooding.app.service.user.store;

import im.fooding.app.dto.request.user.store.UserRetrieveStoreRequest;
import im.fooding.app.dto.response.user.store.StoreResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.service.store.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreApplicationService {

    private final StoreService storeService;

    @Transactional(readOnly = true)
    public PageResponse<StoreResponse> list(UserRetrieveStoreRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum()-1, request.getPageSize());

        Page<Store> list = storeService.list(
                pageable,
                request.getSortType(),
                request.getSortDirection()
        );

        List<StoreResponse> storeResponses = list.stream()
                .map(this::mapToStoreResponse)
                .toList();

        return PageResponse.of(storeResponses, PageInfo.of(list));
    }

    private StoreResponse mapToStoreResponse(Store store) {
        float reviewScore = calculateAverageReviewScore(store);
        int estimatedWaitingTime = calculateEstimatedWaitingTime(store);
        return StoreResponse.of(store, reviewScore, estimatedWaitingTime);
    }

    private float calculateAverageReviewScore(Store store) {
        return (float) store.getReviews().stream()
                .mapToDouble(review -> review.getScore().getTotal())
                .average()
                .orElse(0.0);
    }

    private int calculateEstimatedWaitingTime(Store store) {
        return store.getWaitings().stream()
                .flatMap(w -> w.getWaitingSettings().stream())
                .filter(WaitingSetting::isActive)
                .mapToInt(WaitingSetting::getEstimatedWaitingTimeMinutes)
                .min()
                .orElse(0);
    }
}
