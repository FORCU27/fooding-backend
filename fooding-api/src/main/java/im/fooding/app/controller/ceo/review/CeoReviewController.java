package im.fooding.app.controller.ceo.review;

import im.fooding.app.dto.request.ceo.review.CeoCreateReviewRequest;
import im.fooding.app.dto.request.ceo.review.CeoReviewRequest;
import im.fooding.app.dto.request.ceo.review.CeoUpdateReviewRequest;
import im.fooding.app.dto.response.ceo.review.CeoReviewResponse;
import im.fooding.app.service.ceo.review.CeoReviewService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping( "/ceo/review" )
@Tag( name = "CeoReviewController", description = "CEO들이 사용하는 리뷰 관련 컨트롤러")
public class CeoReviewController {
    private final CeoReviewService service;

    @GetMapping()
    @Operation( summary = "리뷰 목록 조회" )
    public ApiResult<PageResponse<CeoReviewResponse>> list(
            @ModelAttribute CeoReviewRequest request
    ){
        log.info( "store id: {}", request.getStoreId()  );
        return ApiResult.ok( service.list( request ) );
    }

    @PostMapping()
    @Operation( summary = "답글 생성" )
    public ApiResult<Void> createReply(
            @RequestBody CeoCreateReviewRequest request,
            @AuthenticationPrincipal UserInfo userInfo
    ){

        service.createReview( request, userInfo.getId() );
        return ApiResult.ok();
    }

    @PatchMapping("/{id}")
    @Operation( summary = "답글 수정" )
    public ApiResult<Void> updateReply(
            @RequestBody CeoUpdateReviewRequest request,
            @PathVariable Long id
    ){
        service.updateReview( id, request );
        return ApiResult.ok();
    }

}
