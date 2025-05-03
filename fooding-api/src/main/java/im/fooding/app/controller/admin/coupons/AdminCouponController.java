package im.fooding.app.controller.admin.coupons;

import im.fooding.app.dto.request.admin.coupon.AdminCreateCouponRequest;
import im.fooding.app.dto.request.admin.coupon.AdminSearchCouponRequest;
import im.fooding.app.dto.request.admin.coupon.AdminUpdateCouponRequest;
import im.fooding.app.dto.response.admin.coupon.AdminCouponResponse;
import im.fooding.app.service.admin.coupon.AdminCouponService;
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
@RequestMapping("/admin/coupons")
@Tag(name = "AdminCouponController", description = "관리자 쿠폰 컨트롤러")
public class AdminCouponController {
    private final AdminCouponService service;

    @PostMapping
    @Operation(summary = "쿠폰 등록")
    public ApiResult<Void> create(@Valid @RequestBody AdminCreateCouponRequest request) {
        service.create(request);
        return ApiResult.ok();
    }

    @GetMapping
    @Operation(summary = "쿠폰 리스트 조회")
    public ApiResult<PageResponse<AdminCouponResponse>> list(@Valid AdminSearchCouponRequest search) {
        return ApiResult.ok(service.list(search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "쿠폰 조회")
    public ApiResult<AdminCouponResponse> list(@PathVariable Long id) {
        return ApiResult.ok(service.retrieve(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "쿠폰 수정")
    public ApiResult<Void> update(@PathVariable Long id, @Valid @RequestBody AdminUpdateCouponRequest request) {
        service.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "쿠폰 삭제")
    public ApiResult<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
