package im.fooding.app.service.ceo.review;

import im.fooding.app.dto.request.ceo.review.CeoReviewRequest;
import im.fooding.app.dto.response.ceo.review.CeoReviewResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CeoReviewService {
    private final ReviewService reviewService;
    private final StoreService storeService;

    public PageResponse<CeoReviewResponse> list(CeoReviewRequest request){
        // 최상위 리뷰만 가져옴
        Page<Review> result = reviewService.list( request.getStoreId(), null, 0L, request.getPageable() );
        // Store의 CEO ID 조회
        Store store = storeService.findById( request.getStoreId() );
        List<CeoReviewResponse> reviewList = result.map( review -> {
            // 각 최상위 리뷰에 달린 하위 리뷰를 가져옴
            CeoReviewResponse temp = CeoReviewResponse.of( review );
            Review ceoReply = reviewService.findCeoReply( store, store.getOwner(), review );
            if( ceoReply != null ){
                CeoReviewResponse reply = CeoReviewResponse.of( ceoReply );
                temp.addReply( reply );
            }
            return temp;
        }).stream().toList();
        return PageResponse.of( reviewList, PageInfo.of( result ) );
    }
}
