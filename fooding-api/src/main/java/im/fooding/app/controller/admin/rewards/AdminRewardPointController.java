package im.fooding.app.controller.admin.rewards;

import im.fooding.app.dto.request.admin.reward.AdminCreateRewardPointRequest;
import im.fooding.app.dto.request.admin.reward.AdminSearchRewardPointRequest;
import im.fooding.app.dto.request.admin.reward.AdminUpdateRewardPointRequest;
import im.fooding.app.dto.response.admin.reward.AdminRewardPointResponse;
import im.fooding.app.service.admin.reward.AdminRewardPointService;
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
@RequestMapping("/admin/rewards/points")
@Tag(name = "AdminRewardPointController", description = "관리자 리워드 포인트 컨트롤러")
public class AdminRewardPointController {
    private final AdminRewardPointService adminRewardPointService;

    @GetMapping
    @Operation(summary = "리워드 포인트 전체 조회")
    public ApiResult<PageResponse<AdminRewardPointResponse>> list(@Valid AdminSearchRewardPointRequest request) {
        PageResponse<AdminRewardPointResponse> points = adminRewardPointService.list(request);
        return ApiResult.ok(points);
    }

    @GetMapping("/{id}")
    @Operation(summary = "리워드 포인트 상세 조회")
    public ApiResult<AdminRewardPointResponse> retrieve(@PathVariable Long id) {
        AdminRewardPointResponse point = adminRewardPointService.retrieve(id);
        return ApiResult.ok(point);
    }

    @PostMapping
    @Operation(summary = "리워드 포인트 생성")
    public ApiResult<Long> create(@RequestBody @Valid AdminCreateRewardPointRequest request) {
        Long id = adminRewardPointService.create(request);
        return ApiResult.ok(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "리워드 포인트 수정")
    public ApiResult<Void> update(@PathVariable Long id, @RequestBody @Valid AdminUpdateRewardPointRequest request) {
        adminRewardPointService.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "리워드 포인트 삭제")
    public ApiResult<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        adminRewardPointService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }

    @PutMapping("/{id}/add-point")
    @Operation(summary = "포인트 추가")
    public ApiResult<Void> addPoint(@PathVariable Long id, @RequestParam int point) {
        adminRewardPointService.addPoint(id, point);
        return ApiResult.ok();
    }

    @PutMapping("/{id}/use-point")
    @Operation(summary = "포인트 사용")
    public ApiResult<Void> usePoint(@PathVariable Long id, @RequestParam int point) {
        adminRewardPointService.usePoint(id, point);
        return ApiResult.ok();
    }
}
