package im.fooding.app.controller.ceo.review;

import im.fooding.app.dto.request.ceo.review.CeoReplyRequest;
import im.fooding.app.dto.request.ceo.review.CeoReplyUpdateRequest;
import im.fooding.app.dto.request.ceo.review.CeoReviewRequest;
import im.fooding.app.dto.response.ceo.review.CeoReviewResponse;
import im.fooding.app.service.ceo.review.CeoReviewService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/ceo/review" )
@Tag( name = "CeoReviewController", description = "CEO들이 사용하는 리뷰 관련 컨트롤러")
public class CeoReviewController {
    private final CeoReviewService service;

    @GetMapping()
    @Operation( summary = "CEO들의 리뷰 조회 API")
    public ApiResult<PageResponse<CeoReviewResponse>> list(
            @ModelAttribute CeoReviewRequest request
    ){
        return ApiResult.ok( service.list( request ) );
    }

    @PostMapping("/reply/{reviewId}")
    @Operation( summary = "CEO들의 리뷰에 대한 답글 API" )
    public ApiResult<Void> reply(
            @PathVariable Long reviewId,
            @Valid @RequestBody CeoReplyRequest request
    ) {
        service.reply( reviewId, request );
        return ApiResult.ok();
    }

    @PatchMapping( "/reply/{replyId}")
    @Operation( summary = "CEO들의 답글 수정" )
    public ApiResult<Void> updateReply(
            @PathVariable Long replyId,
            @Valid @RequestBody CeoReplyUpdateRequest request
    ){
        service.updateReply( replyId, request );
        return ApiResult.ok();
    }

    @DeleteMapping( "/reply/{reviewId}" )
    @Operation( summary = "CEO들의 답글 삭제" )
    public ApiResult<Void> delete(
            @PathVariable Long reviewId,
            @RequestParam Long deletedBy
    ){
        service.deleteReply( reviewId, deletedBy );
        return ApiResult.ok();
    }
}
