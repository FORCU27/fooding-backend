package im.fooding.app.controller.app.reward;

import im.fooding.app.dto.request.app.coupon.AppSearchCouponRequest;
import im.fooding.app.dto.request.user.reward.GetRewardLogRequest;
import im.fooding.app.dto.request.user.reward.GetRewardPointRequest;
import im.fooding.app.dto.request.user.reward.UpdateRewardPointRequest;
import im.fooding.app.dto.response.app.coupon.AppUserCouponResponse;
import im.fooding.app.dto.response.user.reward.GetRewardLogResponse;
import im.fooding.app.dto.response.user.reward.GetRewardPointResponse;
import im.fooding.app.service.user.reward.RewardApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/rewards")
@Tag(name = "AppRewardController", description = "[APP] 리워드 컨트롤러")
public class AppRewardController {
    private final RewardApplicationService service;

    @GetMapping("/point")
    @Operation(summary = "리워드 내역 조회", description = "특정 가게에 누적된 리워드 포인트 확인")
    public ApiResult<Page<GetRewardPointResponse>> getRewardPoint(
            @ModelAttribute GetRewardPointRequest request
    ) {
        return ApiResult.ok(service.getRewardPoint(request));
    }

    @GetMapping("/log")
    @Operation(summary = "리워드 로그 내역 조회")
    public ApiResult<Page<GetRewardLogResponse>> getRewardLog(
            @ModelAttribute GetRewardLogRequest request
    ) {
        return ApiResult.ok(service.getRewardLog(request));
    }

    @PostMapping("/use")
    @Operation(summary = "포인트 사용")
    public ApiResult<Void> usePoint(
            @RequestBody UpdateRewardPointRequest request
    ) {
        service.usePoint(request);
        return ApiResult.ok();
    }

    @PostMapping("/get")
    @Operation(summary = "포인트 적립")
    public ApiResult<Void> getPoint(
            @RequestBody UpdateRewardPointRequest request
    ) {
        service.earnPoint(request);
        return ApiResult.ok();
    }

    @GetMapping("/coupons")
    @Operation(summary = "쿠폰 조회")
    public ApiResult<PageResponse<AppUserCouponResponse>> getRewardCoupons(@Valid AppSearchCouponRequest request) {
        return ApiResult.ok(service.getRewardCoupons(request));
    }

    @PostMapping("/coupons/{id}/request")
    @Operation(summary = "쿠폰 사용 요청")
    public ApiResult<Void> requestCoupon(@PathVariable Long id) {
        service.requestCoupon(id);
        return ApiResult.ok();
    }
}
