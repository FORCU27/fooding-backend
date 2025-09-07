package im.fooding.app.controller.admin.waiting;

import im.fooding.app.dto.request.admin.waiting.AdminWaitingSettingCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingSettingUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingSettingResponse;
import im.fooding.app.service.admin.waiting.AdminWaitingSettingService;
import im.fooding.core.common.ApiResult;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingSettingListRequest;
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
@RequestMapping("/admin/waitings/settings")
@Tag(name = "AdminWaitingSettingController", description = "[관리자] 웨이팅 설정 컨트롤러")
@Slf4j
public class AdminWaitingSettingController {

    private final AdminWaitingSettingService adminWaitingSettingService;

    @PostMapping
    @Operation(summary = "웨이팅 설정 생성")
    public ApiResult<Void> create(
            @RequestBody @Valid AdminWaitingSettingCreateRequest request
    ) {
        adminWaitingSettingService.create(request);
        return ApiResult.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "웨이팅 설정 조회")
    public ApiResult<AdminWaitingSettingResponse> get(
            @PathVariable long id
    ) {
        return ApiResult.ok(adminWaitingSettingService.get(id));
    }

    @GetMapping
    @Operation(summary = "웨이팅 설정 조회(page)")
    public ApiResult<PageResponse<AdminWaitingSettingResponse>> list(
            @Parameter(description = "검색 및 페이징 조건 (waitingId, isActive 포함)")
            @ModelAttribute AdminWaitingSettingListRequest search
    ) {
        return ApiResult.ok(adminWaitingSettingService.list(search));
    }

    @PutMapping("/{id}")
    @Operation(summary = "웨이팅 설정 수정")
    public ApiResult<Void> update(
            @PathVariable long id,

            @RequestBody @Valid AdminWaitingSettingUpdateRequest request
    ) {
        adminWaitingSettingService.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "웨이팅 설정 제거")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable long id
    ) {
        adminWaitingSettingService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
