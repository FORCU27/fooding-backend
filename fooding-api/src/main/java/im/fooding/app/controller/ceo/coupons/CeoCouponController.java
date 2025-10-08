package im.fooding.app.controller.ceo.coupons;

import im.fooding.app.dto.request.ceo.coupon.CeoCreateCouponRequest;
import im.fooding.app.dto.request.ceo.coupon.CeoSearchCouponRequest;
import im.fooding.app.dto.request.ceo.coupon.CeoSearchUserCouponRequest;
import im.fooding.app.dto.request.ceo.coupon.CeoUpdateCouponRequest;
import im.fooding.app.dto.response.ceo.coupon.CeoCouponResponse;
import im.fooding.app.dto.response.ceo.coupon.CeoUserCouponResponse;
import im.fooding.app.service.ceo.coupon.CeoCouponService;
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
@RequestMapping("/ceo/coupons")
@Tag(name = "CeoCouponController", description = "Ceo 쿠폰 컨트롤러")
public class CeoCouponController {
    private final CeoCouponService service;

    @PostMapping
    @Operation(summary = "쿠폰 등록")
    public ApiResult<Long> create(@Valid @RequestBody CeoCreateCouponRequest request, @AuthenticationPrincipal UserInfo user) {
        return ApiResult.ok(service.create(request, user.getId()));
    }

    @GetMapping
    @Operation(summary = "쿠폰 리스트 조회")
    public ApiResult<PageResponse<CeoCouponResponse>> list(@Valid CeoSearchCouponRequest search, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(search, userInfo.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "쿠폰 조회")
    public ApiResult<CeoCouponResponse> retrieve(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(id, userInfo.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "쿠폰 수정")
    public ApiResult<Void> update(@PathVariable Long id, @Valid @RequestBody CeoUpdateCouponRequest request, @AuthenticationPrincipal UserInfo user) {
        service.update(id, request, user.getId());
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "쿠폰 삭제")
    public ApiResult<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }

    @GetMapping("/{id}/usages")
    @Operation(summary = "쿠폰 사용내역 조회")
    public ApiResult<PageResponse<CeoUserCouponResponse>> getUsages(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo, @Valid CeoSearchUserCouponRequest search) {
        return ApiResult.ok(service.getUsages(id, userInfo.getId(), search));
    }
}
