package im.fooding.app.controller.admin.pointshop;

import im.fooding.app.dto.request.admin.pointshop.AdminCreatePointShopRequest;
import im.fooding.app.dto.request.admin.pointshop.AdminSearchPointShopRequest;
import im.fooding.app.dto.request.admin.pointshop.AdminUpdatePointShopRequest;
import im.fooding.app.dto.response.admin.pointshop.AdminPointShopResponse;
import im.fooding.app.service.admin.store.AdminStorePointShopService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/point-shop")
@Tag(name = "AdminStorePointShopController", description = "ADMIN Store 포인트샵 컨트롤러")
public class AdminStorePointShopController {
    private final AdminStorePointShopService service;

    @GetMapping
    @Operation(summary = "포인트샵 상품 리스트 조회")
    public ApiResult<PageResponse<AdminPointShopResponse>> list(@Valid AdminSearchPointShopRequest search) {
        return ApiResult.ok(service.list(search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "포인트샵 상품 상세 조회")
    public ApiResult<AdminPointShopResponse> retrieve(@PathVariable long id) {
        return ApiResult.ok(service.retrieve(id));
    }

    @PostMapping
    @Operation(summary = "포인트샵 상품 등록")
    public ApiResult<Long> create(@RequestBody @Valid AdminCreatePointShopRequest request) {
        return ApiResult.ok(service.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "포인트샵 상품 수정")
    public ApiResult<Void> update(@PathVariable long id,
                                  @RequestBody @Valid AdminUpdatePointShopRequest request) {
        service.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "포인트샵 상품 삭제")
    public ApiResult<Void> delete(@PathVariable long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }

    @PutMapping("/{id}/active")
    @Operation(summary = "포인트샵 상품 판매 상태로 변경")
    public ApiResult<Void> active(@PathVariable long id) {
        service.active(id);
        return ApiResult.ok();
    }

    @PutMapping("/{id}/inactive")
    @Operation(summary = "포인트샵 상품 판매중지 상태로 변경")
    public ApiResult<Void> inactive(@PathVariable long id) {
        service.inactive(id);
        return ApiResult.ok();
    }
}
