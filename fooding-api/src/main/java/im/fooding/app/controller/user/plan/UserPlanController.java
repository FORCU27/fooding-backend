package im.fooding.app.controller.user.plan;

import im.fooding.app.dto.request.user.plan.UserPlanRetrieveRequest;
import im.fooding.app.dto.response.user.plan.UserPlanResponse;
import im.fooding.app.service.user.plan.UserPlanService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/plans")
@Tag(name = "UserPlanController", description = "유저 플랜 컨트롤러")
public class UserPlanController {

    private final UserPlanService userPlanService;

    @GetMapping
    @Operation(summary = "플랜 목록 조회")
    ApiResult<PageResponse<UserPlanResponse>> list(
            @AuthenticationPrincipal UserInfo userInfo,
            @ModelAttribute UserPlanRetrieveRequest request
    ) {
        return ApiResult.ok(userPlanService.list(request, userInfo.getId()));
    }
}
