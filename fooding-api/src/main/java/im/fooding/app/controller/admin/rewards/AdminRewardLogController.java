package im.fooding.app.controller.admin.rewards;

import im.fooding.app.dto.request.admin.reward.AdminCreateRewardLogRequest;
import im.fooding.app.dto.request.admin.reward.AdminSearchRewardLogRequest;
import im.fooding.app.dto.request.admin.reward.AdminUpdateRewardLogRequest;
import im.fooding.app.dto.response.admin.reward.AdminRewardLogResponse;
import im.fooding.app.service.admin.reward.AdminRewardLogService;
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
@RequestMapping("/admin/rewards/logs")
@Tag(name = "AdminRewardLogController", description = "관리자 리워드 로그 컨트롤러")
public class AdminRewardLogController {
    private final AdminRewardLogService adminRewardLogService;

    @GetMapping
    @Operation(summary = "리워드 로그 전체 조회")
    public ApiResult<PageResponse<AdminRewardLogResponse>> list(@Valid AdminSearchRewardLogRequest request) {
        PageResponse<AdminRewardLogResponse> logs = adminRewardLogService.list(request);
        return ApiResult.ok(logs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "리워드 로그 상세 조회")
    public ApiResult<AdminRewardLogResponse> retrieve(@PathVariable Long id) {
        AdminRewardLogResponse log = adminRewardLogService.retrieve(id);
        return ApiResult.ok(log);
    }

    @PostMapping
    @Operation(summary = "리워드 로그 생성")
    public ApiResult<Long> create(@RequestBody @Valid AdminCreateRewardLogRequest request) {
        Long id = adminRewardLogService.create(request);
        return ApiResult.ok(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "리워드 로그 수정")
    public ApiResult<Void> update(@PathVariable Long id, @RequestBody @Valid AdminUpdateRewardLogRequest request) {
        adminRewardLogService.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "리워드 로그 삭제")
    public ApiResult<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        adminRewardLogService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "리워드 로그 상태 변경")
    public ApiResult<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        adminRewardLogService.updateStatus(id, status);
        return ApiResult.ok();
    }
}
