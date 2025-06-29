package im.fooding.app.controller.user.coupons;

import im.fooding.app.dto.request.user.coupon.UserSearchStoreCouponRequest;
import im.fooding.app.dto.request.user.store.UserSearchStoreRequest;
import im.fooding.app.dto.response.user.coupon.UserStoreCouponResponse;
import im.fooding.app.dto.response.user.store.UserStoreListResponse;
import im.fooding.app.service.user.coupon.UserStoreCouponService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
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
@RequestMapping("/user/store-coupons")
@Tag(name = "UserStoreCouponController", description = "유저 가게의 쿠폰 컨트롤러")
public class UserStoreCouponController {
    private final UserStoreCouponService service;

    @GetMapping
    @Operation(summary = "가게의 쿠폰 목록 조회")
    public ApiResult<PageResponse<UserStoreCouponResponse>> list(@Valid UserSearchStoreCouponRequest search) {
        return ApiResult.ok(service.list(search));
    }

    @PostMapping("/{id}/issue")
    @Operation(summary = "가게의 쿠폰 발급 받기")
    public ApiResult<Long> issue(@PathVariable long id, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.issue(id, userInfo.getId()));
    }
}
