package im.fooding.app.controller.user.my;

import im.fooding.app.dto.request.user.review.UserRetrieveReviewRequest;
import im.fooding.app.dto.response.user.review.UserReviewResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/my")
@Tag(name = "UserMyController", description = "내 정보 컨트롤러")
@Slf4j
public class UserMyController {

    private final UserReviewService userReviewService;

    @GetMapping("/reviews")
    @Operation(summary = "내 리뷰 목록 조회", description = "로그인한 사용자의 리뷰를 조회합니다.")
    public ApiResult<PageResponse<UserReviewResponse>> listMyReviews(
            @AuthenticationPrincipal UserInfo userInfo,
            @Valid @ModelAttribute UserRetrieveReviewRequest request
    ) {
        request.setWriterId(userInfo.getId());
        return ApiResult.ok(userReviewService.list(null, request));
    }
}
