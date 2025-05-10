package im.fooding.app.controller.admin.coupons;

import im.fooding.app.dto.request.admin.coupon.AdminGiftCouponRequest;
import im.fooding.app.dto.request.admin.coupon.AdminIssueCouponRequest;
import im.fooding.app.dto.request.admin.coupon.AdminSearchUserCouponRequest;
import im.fooding.app.dto.response.admin.coupon.AdminUserCouponResponse;
import im.fooding.app.service.admin.coupon.AdminUserCouponService;
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
@RequestMapping("/admin/user-coupons")
@Tag(name = "AdminUserCouponController", description = "관리자 유저의 쿠폰 컨트롤러")
public class AdminUserCouponController {
    private final AdminUserCouponService service;

    @PostMapping
    @Operation(summary = "쿠폰 발급")
    public ApiResult<Void> issue(@Valid @RequestBody AdminIssueCouponRequest request) {
        service.issue(request);
        return ApiResult.ok();
    }

    @PostMapping("/gift")
    @Operation(summary = "쿠폰 선물하기")
    public ApiResult<Void> issueByGift(@Valid @RequestBody AdminGiftCouponRequest request) {
        service.issueByGift(request);
        return ApiResult.ok();
    }

    @GetMapping
    @Operation(summary = "발급된 쿠폰 리스트 조회")
    public ApiResult<PageResponse<AdminUserCouponResponse>> list(@Valid AdminSearchUserCouponRequest search) {
        return ApiResult.ok(service.list(search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "발급된 쿠폰 조회")
    public ApiResult<AdminUserCouponResponse> retrieve(@PathVariable Long id) {
        return ApiResult.ok(service.retrieve(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "발급된 쿠폰 삭제")
    public ApiResult<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
