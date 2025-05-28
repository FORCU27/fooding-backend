package im.fooding.app.controller.ceo.review;

import im.fooding.app.dto.request.ceo.review.CeoReviewRequest;
import im.fooding.app.dto.response.ceo.review.CeoReviewResponse;
import im.fooding.app.service.ceo.review.CeoReviewService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/ceo/review" )
@Tag( name = "CeoReviewController", description = "CEO들이 사용하는 리뷰 관련 컨트롤러")
public class CeoReviewController {
    private final CeoReviewService service;

    @GetMapping()
    public ApiResult<PageResponse<CeoReviewResponse>> list(
            @ModelAttribute CeoReviewRequest request
    ){
        return ApiResult.ok( service.list( request ) );
    }
}
