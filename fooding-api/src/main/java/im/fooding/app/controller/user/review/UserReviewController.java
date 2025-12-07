package im.fooding.app.controller.user.review;

import im.fooding.app.dto.request.user.report.CreateReportRequest;
import im.fooding.app.dto.request.user.review.CreateReviewRequest;
import im.fooding.app.dto.request.user.review.CreateUserReviewCommentRequest;
import im.fooding.app.dto.request.user.review.UpdateReviewRequest;
import im.fooding.app.dto.request.user.review.UserRetrieveReviewRequest;
import im.fooding.app.dto.response.user.review.UserReviewDetailResponse;
import im.fooding.app.dto.response.user.review.UserReviewResponse;
import im.fooding.app.service.user.report.UserReportService;
import im.fooding.app.service.user.review.UserReviewService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/reviews")
@Tag(name = "UserReviewController", description = "유저 리뷰 컨트롤러")
@Slf4j
public class UserReviewController {

    private final UserReviewService userReviewService;
    private final UserReportService userReportService;

    @GetMapping("/store/{storeId}")
    @Operation(summary = "리뷰 전체 조회", description = "별점순 / 최신순으로 리뷰를 조회합니다.")
    public ApiResult<PageResponse<UserReviewResponse>> list(
            @PathVariable Long storeId,
            @Valid @ParameterObject @ModelAttribute UserRetrieveReviewRequest request
    ) {
        return ApiResult.ok(userReviewService.list(storeId, request));
    }

    @PostMapping( "" )
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
            @RequestBody CreateReportRequest request
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
    @Operation(summary = "리뷰 삭제", description = "특정 리뷰를 삭제합니다.")
    public ApiResult<Void> deleteReview(
            @PathVariable long reviewId,
            @AuthenticationPrincipal UserInfo userInfo
    ){
        userReviewService.delete( reviewId, userInfo.getId() );
        return ApiResult.ok();
    }

    @GetMapping("/{reviewId}/details")
    @Operation(summary = "특정 리뷰에 대한 상세 조회", description = "특정 리뷰 상세 페이지에 필요한 정보를 조회합니다")
    public ApiResult<UserReviewDetailResponse> getReviewDetail(
            @PathVariable Long reviewId
    ){
        return ApiResult.ok( userReviewService.getReviewDetail( reviewId ) );
    }

    @PostMapping("/{reviewId}/like" )
    @Operation(summary = "현재 리뷰에 좋아요 달기/해제", description = "현재 접속한 사용자가 좋아요를 등록/해제. 같은 API로 이미 좋아요 되어 있으면 해제, 좋아요가 되어 있지 않으면 등록")
    public ApiResult<Void> setReviewLike(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserInfo userInfo
    ){
        if( userInfo == null ) throw new ApiException(ErrorCode.AUTHENTICATION_INVALID_ERROR);
        userReviewService.setReviewLike( reviewId, userInfo.getId() );
        return ApiResult.ok();
    }

    @PostMapping("/{reviewId}/comment")
    @Operation(summary = "현재 리뷰에 댓글을 다는 API", description = "답글도 같은 API를 사용합니다. request에 parentId로 null을 주시면 최상위 댓글로 기록되며 댓글의 ID를 입력해주시면 해당 댓글의 하위 댓글로 등록됩니다" )
    public ApiResult<Void> addComment(
            @Valid @ModelAttribute CreateUserReviewCommentRequest request,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserInfo userInfo
    ){
        userReviewService.createComment( reviewId, userInfo.getId(), request );
        return ApiResult.ok();
    }


}
