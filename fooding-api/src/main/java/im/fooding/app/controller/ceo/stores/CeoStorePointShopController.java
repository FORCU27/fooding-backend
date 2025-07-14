package im.fooding.app.controller.ceo.stores;

import im.fooding.app.dto.request.ceo.store.pointshop.CeoCreatePointShopRequest;
import im.fooding.app.dto.request.ceo.store.pointshop.CeoSearchPointShopRequest;
import im.fooding.app.dto.request.ceo.store.pointshop.CeoUpdatePointShopRequest;
import im.fooding.app.dto.response.ceo.store.CeoPointShopResponse;
import im.fooding.app.service.ceo.store.CeoStorePointShopService;
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
@RequestMapping("/ceo/stores")
@Tag(name = "CeoStorePointShopController", description = "Ceo Store 포인트샵 컨트롤러")
public class CeoStorePointShopController {
    private final CeoStorePointShopService service;

    @GetMapping("/{storeId}/point-shop")
    @Operation(summary = "포인트샵 상품 리스트 조회")
    public ApiResult<PageResponse<CeoPointShopResponse>> list(@PathVariable long storeId,
                                                              @Valid CeoSearchPointShopRequest search,
                                                              @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(storeId, search, userInfo.getId()));
    }

    @GetMapping("/{storeId}/point-shop/{id}")
    @Operation(summary = "포인트샵 상품 상세 조회")
    public ApiResult<CeoPointShopResponse> retrieve(@PathVariable long storeId,
                                                    @PathVariable long id,
                                                    @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(storeId, id, userInfo.getId()));
    }

    @PostMapping("/{storeId}/point-shop")
    @Operation(summary = "포인트샵 상품 등록")
    public ApiResult<Long> create(@PathVariable long storeId,
                                  @RequestBody @Valid CeoCreatePointShopRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.create(storeId, request, userInfo.getId()));
    }

    @PutMapping("/{storeId}/point-shop/{id}")
    @Operation(summary = "포인트샵 상품 수정")
    public ApiResult<Void> update(@PathVariable long storeId, @PathVariable long id,
                                  @RequestBody @Valid CeoUpdatePointShopRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        service.update(storeId, id, request, userInfo.getId());
        return ApiResult.ok();
    }

    @DeleteMapping("/{storeId}/point-shop/{id}")
    @Operation(summary = "포인트샵 상품 삭제")
    public ApiResult<Void> delete(@PathVariable long storeId,
                                  @PathVariable long id,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(storeId, id, userInfo.getId());
        return ApiResult.ok();
    }

    @PutMapping("/{storeId}/point-shop/{id}/active")
    @Operation(summary = "포인트샵 상품 판매 상태로 변경")
    public ApiResult<Void> active(@PathVariable long storeId,
                                  @PathVariable long id,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        service.active(storeId, id, userInfo.getId());
        return ApiResult.ok();
    }

    @PutMapping("/{storeId}/point-shop/{id}/inactive")
    @Operation(summary = "포인트샵 상품 판매중지 상태로 변경")
    public ApiResult<Void> inactive(@PathVariable long storeId,
                                    @PathVariable long id,
                                    @AuthenticationPrincipal UserInfo userInfo) {
        service.inactive(storeId, id, userInfo.getId());
        return ApiResult.ok();
    }
}
