package im.fooding.app.controller.admin.stores;

import im.fooding.app.dto.request.admin.store.AdminCreateStoreRequest;
import im.fooding.app.dto.request.admin.store.AdminSearchStoreRequest;
import im.fooding.app.dto.request.admin.store.AdminUpdateStoreRequest;
import im.fooding.app.dto.response.admin.store.AdminStoreResponse;
import im.fooding.app.service.admin.store.AdminStoreService;
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
@RequestMapping("/admin/stores")
@Tag(name = "AdminStoreController", description = "관리자 가게 컨트롤러")
public class AdminStoreController {
    private final AdminStoreService adminStoreService;

    @GetMapping
    @Operation(summary = "가게 전체 조회")
    public ApiResult<PageResponse<AdminStoreResponse>> list(@Valid AdminSearchStoreRequest request) {
        PageResponse<AdminStoreResponse> stores = adminStoreService.list(request);
        return ApiResult.ok(stores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "가게 상세 조회")
    public ApiResult<AdminStoreResponse> retrieve(@PathVariable Long id) {
        AdminStoreResponse store = adminStoreService.retrieve(id);
        return ApiResult.ok(store);
    }

    @PostMapping
    @Operation(summary = "가게 생성")
    public ApiResult<Long> create(@RequestBody @Valid AdminCreateStoreRequest request) {
        Long id = adminStoreService.create(request);
        return ApiResult.ok(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "가게 수정")
    public ApiResult<Void> update(@PathVariable Long id, @RequestBody @Valid AdminUpdateStoreRequest request) {
        adminStoreService.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "가게 삭제")
    public ApiResult<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        adminStoreService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
