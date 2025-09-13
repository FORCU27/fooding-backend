package im.fooding.app.controller.user.review;

import im.fooding.app.dto.request.user.report.CreateReportRequest;
import im.fooding.app.dto.request.user.review.CreateReviewRequest;
import im.fooding.app.dto.request.user.review.UpdateReviewRequest;
import im.fooding.app.dto.request.user.review.UserRetrieveReviewRequest;
import im.fooding.app.dto.response.user.review.UserReviewResponse;
import im.fooding.app.service.user.report.UserReportService;
import im.fooding.app.service.user.review.UserReviewService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/stores")
@Tag(name = "UserReviewController", description = "유저 리뷰 컨트롤러")
@Slf4j
public class UserReviewController {

    private final UserReviewService userReviewService;
    private final UserReportService userReportService;

    @GetMapping("/{storeId}/reviews")
    @Operation(summary = "리뷰 전체 조회", description = "별점순 / 최신순으로 리뷰를 조회합니다.")
    public ApiResult<PageResponse<UserReviewResponse>> list(
            @PathVariable Long storeId,
            @Valid @ModelAttribute UserRetrieveReviewRequest request
    ) {
        return ApiResult.ok(userReviewService.list(storeId, request));
    }

    @PostMapping( "/reviews" )
    @Operation( summary = "리뷰 작성", description = "매장에 대한 리뷰를 작성합니다." )
    public ApiResult<Void> create(
            @Valid @RequestBody CreateReviewRequest request
    ){
        userReviewService.create( request );
        return ApiResult.ok();
    }

    @PostMapping( "/{reviewId}/report")
    @Operation(summary = "리뷰 신고", description = "특정 리뷰를 신고합니다.")
    public ApiResult<Void> reportReview(
            @PathVariable long reviewId,
            @ModelAttribute CreateReportRequest request
    ){
        userReportService.createReport( reviewId, request );
        return ApiResult.ok();
    }

    @PatchMapping( "/{reviewId}/update" )
    @Operation(summary = "리뷰 수정", description = "특정 리뷰를 수정합니다.")
    public ApiResult<Void> updateReview(
            @PathVariable long reviewId,
            @RequestBody UpdateReviewRequest request
    ){
        userReviewService.update( reviewId, request );
        return ApiResult.ok();
    }

    @DeleteMapping( "/{reviewId}" )
    @Operation(summary = "리뷰 삭제", description = "특정 리뷰를 삭제합니다..")
    public ApiResult<Void> deleteReview(
            @PathVariable long reviewId,
            @AuthenticationPrincipal UserInfo userInfo
    ){
        userReviewService.delete( reviewId, userInfo.getId() );
        return ApiResult.ok();
    }

}
