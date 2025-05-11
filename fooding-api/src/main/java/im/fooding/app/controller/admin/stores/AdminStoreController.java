package im.fooding.app.controller.admin.stores;

import im.fooding.app.dto.request.admin.store.AdminCreateStoreRequest;
import im.fooding.app.dto.request.admin.store.AdminUpdateStoreRequest;
import im.fooding.app.dto.response.admin.store.AdminStoreResponse;
import im.fooding.app.service.admin.store.AdminStoreService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.StoreSortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ApiResult<PageResponse<AdminStoreResponse>> list(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "RECENT") StoreSortType sortType,
            @RequestParam(required = false, defaultValue = "DESCENDING") SortDirection sortDirection) {
        PageResponse<AdminStoreResponse> stores = adminStoreService.list(pageable, sortType, sortDirection);
        return ApiResult.ok(stores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "가게 상세 조회")
    public ApiResult<AdminStoreResponse> findById(@PathVariable Long id) {
        AdminStoreResponse store = adminStoreService.findById(id);
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
    public ApiResult<Void> delete(@PathVariable Long id) {
        adminStoreService.delete(id);
        return ApiResult.ok();
    }
}