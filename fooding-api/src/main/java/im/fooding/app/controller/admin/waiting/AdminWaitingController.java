package im.fooding.app.controller.admin.waiting;

import im.fooding.app.dto.request.admin.waiting.AdminWaitingCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingUpdateRequest;
import im.fooding.app.dto.response.waiting.WaitingResponse;
import im.fooding.app.service.admin.waiting.AdminWaitingService;
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
@RequestMapping("/admin/waitings")
@Tag(name = "AdminWaitingController", description = "웨이팅 관리 컨트롤러")
@Slf4j
public class AdminWaitingController {

    private final AdminWaitingService adminWaitingService;

    @PostMapping
    @Operation(summary = "웨이팅 생성")
    public ApiResult<WaitingResponse> createWaiting(
            @RequestBody @Valid AdminWaitingCreateRequest request
    ) {
        WaitingResponse response = adminWaitingService.createWaiting(request);
        return ApiResult.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "웨이팅 조회")
    public ApiResult<WaitingResponse> getWaiting(
            @PathVariable long id
    ) {
        return ApiResult.ok(adminWaitingService.getWaiting(id));
    }

    @GetMapping
    @Operation(summary = "웨이팅 조회(page)")
    public ApiResult<PageResponse<WaitingResponse>> getWaitingList(
            @Parameter(description = "검색 및 페이징 조건")
            @ModelAttribute BasicSearch search
    ) {
        return ApiResult.ok(adminWaitingService.getWaitingList(search));
    }

    @PutMapping
    @Operation(summary = "웨이팅 수정")
    public ApiResult<WaitingResponse> UpdateWaiting(
            @RequestBody AdminWaitingUpdateRequest request
    ) {
        return ApiResult.ok(adminWaitingService.updateWaiting(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "웨이팅 제거")
    public ApiResult<Void> deleteWaiting(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable long id
    ) {
        adminWaitingService.deleteWaiting(id, userInfo.getId());
        return ApiResult.ok();
    }
}
