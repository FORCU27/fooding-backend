package im.fooding.app.controller.user.coupons;

import im.fooding.app.dto.request.user.coupon.UserUseCouponRequest;
import im.fooding.app.dto.request.user.coupon.UserSearchUserCouponRequest;
import im.fooding.app.dto.response.user.coupon.UserCouponResponse;
import im.fooding.app.service.user.coupon.UserCouponAppService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/coupons")
@Tag(name = "UserCouponIssueController", description = "유저가 보유한 쿠폰 컨트롤러")
public class UserCouponController {
    private final UserCouponAppService service;

    @GetMapping
    @Operation(summary = "보유한 쿠폰 목록 조회")
    public ApiResult<PageResponse<UserCouponResponse>> list(@Valid UserSearchUserCouponRequest search, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(search, userInfo.getId()));
    }

    @PostMapping("/{id}/request")
    @Operation(summary = "보유한 쿠폰 사용")
    public ApiResult<Long> request(@PathVariable long id, @AuthenticationPrincipal UserInfo userInfo, @Valid @RequestBody UserUseCouponRequest request) {
        service.request(id, userInfo.getId(), request);
        return ApiResult.ok();
    }
}
