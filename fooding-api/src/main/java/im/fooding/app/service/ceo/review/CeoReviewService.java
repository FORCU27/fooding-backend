package im.fooding.app.service.ceo.review;

import im.fooding.app.dto.request.ceo.review.CeoReviewRequest;
import im.fooding.app.dto.response.ceo.review.CeoReviewResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CeoReviewService {
    private final ReviewService reviewService;
    private final StoreService storeService;

    public PageResponse<CeoReviewResponse> list(CeoReviewRequest request){
        Store store = storeService.findById( request.getStoreId() );
        Page<Review> result = reviewService.list( store, request.getPageable() );
        return PageResponse.of( result.map(CeoReviewResponse::of).stream().toList(), PageInfo.of( result ) );
    }
}
