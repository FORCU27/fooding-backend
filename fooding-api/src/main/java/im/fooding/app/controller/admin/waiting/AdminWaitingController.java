package im.fooding.app.controller.admin.waiting;

import im.fooding.app.dto.request.admin.waiting.AdminWaitingCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingResponse;
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
@Tag(name = "AdminWaitingController", description = "[관리자] 웨이팅 컨트롤러")
@Slf4j
public class AdminWaitingController {

    private final AdminWaitingService adminWaitingService;

    @PostMapping
    @Operation(summary = "웨이팅 생성")
    public ApiResult<AdminWaitingResponse> create(
            @RequestBody @Valid AdminWaitingCreateRequest request
    ) {
        AdminWaitingResponse response = adminWaitingService.create(request);
        return ApiResult.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "웨이팅 조회")
    public ApiResult<AdminWaitingResponse> get(
            @PathVariable long id
    ) {
        return ApiResult.ok(adminWaitingService.get(id));
    }

    @GetMapping
    @Operation(summary = "웨이팅 조회(page)")
    public ApiResult<PageResponse<AdminWaitingResponse>> getList(
            @Parameter(description = "검색 및 페이징 조건")
            @ModelAttribute BasicSearch search
    ) {
        return ApiResult.ok(adminWaitingService.getList(search));
    }

    @PutMapping("/{id}")
    @Operation(summary = "웨이팅 수정")
    public ApiResult<AdminWaitingResponse> update(
            @PathVariable long id,

            @RequestBody AdminWaitingUpdateRequest request
    ) {
        return ApiResult.ok(adminWaitingService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "웨이팅 제거")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable long id
    ) {
        adminWaitingService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
