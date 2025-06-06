package im.fooding.app.controller.admin.waiting;

import im.fooding.app.dto.request.admin.waiting.AdminWaitingLogCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingLogUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingLogResponse;
import im.fooding.app.service.admin.waiting.AdminWaitingLogService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/waitings/logs")
@Tag(name = "AdminWaitingLogController", description = "[관리자] 웨이팅 로그 컨트롤러")
@Slf4j
public class AdminWaitingLogController {

    private final AdminWaitingLogService adminWaitingLogService;

    @PostMapping
    @Operation(summary = "웨이팅 로그 생성")
    public ApiResult<AdminWaitingLogResponse> create(
            @RequestBody @Valid AdminWaitingLogCreateRequest request
    ) {
        adminWaitingLogService.create(request);
        return ApiResult.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "웨이팅 로그 조회")
    public ApiResult<AdminWaitingLogResponse> get(
            @PathVariable long id
    ) {
        return ApiResult.ok(adminWaitingLogService.get(id));
    }

    @GetMapping
    @Operation(summary = "웨이팅 로그 조회(page)")
    public ApiResult<PageResponse<AdminWaitingLogResponse>> list(
            @Parameter(description = "검색 및 페이징 조건")
            @ModelAttribute BasicSearch search
    ) {
        return ApiResult.ok(adminWaitingLogService.list(search));
    }

    @PutMapping("/{id}")
    @Operation(summary = "웨이팅 로그 수정")
    public ApiResult<AdminWaitingLogResponse> update(
            @PathVariable long id,

            @RequestBody @Valid AdminWaitingLogUpdateRequest request
    ) {
        adminWaitingLogService.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "웨이팅 로그 제거")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable long id
    ) {
        adminWaitingLogService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
