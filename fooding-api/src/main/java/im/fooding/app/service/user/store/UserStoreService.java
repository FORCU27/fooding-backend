package im.fooding.app.service.user.store;

import im.fooding.app.dto.request.user.store.UserRetrieveStoreRequest;
import im.fooding.app.dto.response.user.store.UserStoreResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.service.review.ReviewService;
import im.fooding.core.service.store.StoreImageService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.WaitingSettingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserStoreService {

    private final StoreService storeService;
    private final ReviewService reviewService;
    private final WaitingSettingService waitingSettingService;
    private final StoreImageService storeImageService;

    @Transactional(readOnly = true)
    public PageResponse<UserStoreResponse> list(UserRetrieveStoreRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum() - 1, request.getPageSize());

        Page<Store> storePage = storeService.list(
                pageable,
                request.getSortType(),
                request.getSortDirection(),
                false
        );

        List<UserStoreResponse> content = storePage.getContent().stream()
                .map(this::mapStoreToResponse)
                .toList();

        return PageResponse.of(content, PageInfo.of(storePage));
    }

    private UserStoreResponse mapStoreToResponse(Store store) {
        StoreImage storeImage = storeImageService.findByStore(store.getId());
        List<Review> reviews = reviewService.list(store);

        // 대기 시간 비활성화한 경우에는 -1로 반환하도록 구현
        Integer estimatedWaitingTime = waitingSettingService.findActiveSetting(store)
                .map(WaitingSetting::getEstimatedWaitingTimeMinutes)
                .orElse(null);

        return UserStoreResponse.of(
                store,
                storeImage,
                calculateAverageScore(reviews),
                reviews.size(),
                estimatedWaitingTime
        );
    }

    private float calculateAverageScore(List<Review> reviews) {
        return (float) reviews.stream()
                .mapToDouble(review -> review.getScore().getTotal())
                .average()
                .orElse(0);
    }
}
