package im.fooding.app.controller.admin.waiting;

import im.fooding.app.dto.request.admin.waiting.AdminStoreWaitingCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminStoreWaitingUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminStoreWaitingResponse;
import im.fooding.app.service.admin.waiting.AdminStoreWaitingService;
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
@RequestMapping("/admin/waitings/requests")
@Tag(name = "AdminStoreWaitingController", description = "[관리자] 가게 웨이팅 컨트롤러")
@Slf4j
public class StoreWaitingController {

    private final AdminStoreWaitingService adminStoreWaitingService;

    @PostMapping
    @Operation(summary = "가게 웨이팅 생성")
    public ApiResult<AdminStoreWaitingResponse> create(
            @RequestBody @Valid AdminStoreWaitingCreateRequest request
    ) {
        AdminStoreWaitingResponse response = adminStoreWaitingService.create(request);
        return ApiResult.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "가게 웨이팅 조회")
    public ApiResult<AdminStoreWaitingResponse> get(
            @PathVariable long id
    ) {
        return ApiResult.ok(adminStoreWaitingService.get(id));
    }

    @GetMapping
    @Operation(summary = "가게 웨이팅 조회(page)")
    public ApiResult<PageResponse<AdminStoreWaitingResponse>> getList(
            @Parameter(description = "검색 및 페이징 조건")
            @ModelAttribute BasicSearch search
    ) {
        return ApiResult.ok(adminStoreWaitingService.getList(search));
    }

    @PutMapping("/{id}")
    @Operation(summary = "가게 웨이팅 수정")
    public ApiResult<AdminStoreWaitingResponse> update(
            @PathVariable long id,

            @RequestBody AdminStoreWaitingUpdateRequest request
    ) {
        return ApiResult.ok(adminStoreWaitingService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "가게 웨이팅 제거")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable long id
    ) {
        adminStoreWaitingService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
