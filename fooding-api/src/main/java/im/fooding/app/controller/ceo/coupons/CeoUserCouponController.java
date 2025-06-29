package im.fooding.app.controller.ceo.coupons;

import im.fooding.app.dto.request.ceo.coupon.CeoGiftCouponRequest;
import im.fooding.app.dto.request.ceo.coupon.CeoIssueCouponRequest;
import im.fooding.app.dto.request.ceo.coupon.CeoSearchUserCouponRequest;
import im.fooding.app.dto.response.ceo.coupon.CeoUserCouponResponse;
import im.fooding.app.service.ceo.coupon.CeoUserCouponService;
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
@RequestMapping("/ceo/user-coupons")
@Tag(name = "CeoUserCouponController", description = "관리자 유저의 쿠폰 컨트롤러")
public class CeoUserCouponController {
    private final CeoUserCouponService service;

    @PostMapping
    @Operation(summary = "쿠폰 발급")
    public ApiResult<Long> issue(@Valid @RequestBody CeoIssueCouponRequest request, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.issue(request, userInfo.getId()));
    }

    @PostMapping("/gift")
    @Operation(summary = "쿠폰 선물하기")
    public ApiResult<Long> issueByGift(@Valid @RequestBody CeoGiftCouponRequest request, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.issueByGift(request, userInfo.getId()));
    }

    @GetMapping
    @Operation(summary = "발급된 쿠폰 리스트 조회")
    public ApiResult<PageResponse<CeoUserCouponResponse>> list(@Valid CeoSearchUserCouponRequest search, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(search, userInfo.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "발급된 쿠폰 조회")
    public ApiResult<CeoUserCouponResponse> retrieve(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(id, userInfo.getId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "발급된 쿠폰 삭제")
    public ApiResult<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "사용 요청된 쿠폰 승인")
    public ApiResult<Void> approve(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.approve(id, userInfo.getId());
        return ApiResult.ok();
    }
}
