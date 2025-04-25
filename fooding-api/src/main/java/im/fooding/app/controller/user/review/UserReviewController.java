package im.fooding.app.controller.user.review;

import im.fooding.app.dto.request.user.review.UserRetrieveReviewRequest;
import im.fooding.app.dto.response.user.notification.UserNotificationResponse;
import im.fooding.app.dto.response.user.review.UserReviewResponse;
import im.fooding.app.service.user.review.UserReviewService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/store")
@Tag(name = "UserReviewController", description = "유저 리뷰 컨트롤러")
@Slf4j
public class UserReviewController {

    private final UserReviewService userReviewService;

    @GetMapping("{storeId}/reviews")
    @Operation(summary = "리뷰 전체 조회", description = "별점순 / 최신순으로 리뷰를 조회합니다.")
    public ApiResult<PageResponse<UserReviewResponse>> list(
            @PathVariable Long storeId,
            @Valid @RequestBody UserRetrieveReviewRequest request
    ) {
        return ApiResult.ok(userReviewService.list(storeId, request));
    }
}
