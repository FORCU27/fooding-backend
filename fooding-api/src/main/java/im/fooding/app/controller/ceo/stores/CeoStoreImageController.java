package im.fooding.app.controller.ceo.stores;

import im.fooding.app.dto.request.ceo.store.image.CeoCreateStoreImageRequest;
import im.fooding.app.dto.request.ceo.store.image.CeoSearchStoreImageRequest;
import im.fooding.app.dto.request.ceo.store.image.CeoUpdateStoreImageRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreImageResponse;
import im.fooding.app.service.ceo.store.CeoStoreImageService;
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
@Tag(name = "CeoStoreImageController", description = "Ceo Store 사진 컨트롤러")
public class CeoStoreImageController {
    private final CeoStoreImageService service;

    @GetMapping("/{storeId}/images")
    @Operation(summary = "사진 리스트 조회")
    public ApiResult<PageResponse<CeoStoreImageResponse>> list(@PathVariable long storeId, @Valid CeoSearchStoreImageRequest search, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(storeId, search, userInfo.getId()));
    }

    @PostMapping("/{storeId}/images")
    @Operation(summary = "사진 등록")
    public ApiResult<Long> create(@PathVariable long storeId, @RequestBody @Valid CeoCreateStoreImageRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.create(storeId, request, userInfo.getId()));
    }

    @PutMapping("/{storeId}/images/{id}")
    @Operation(summary = "사진 수정")
    public ApiResult<Void> update(@PathVariable long storeId, @PathVariable long id,
                                  @RequestBody @Valid CeoUpdateStoreImageRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        service.update(storeId, id, request, userInfo.getId());
        return ApiResult.ok();
    }

    @DeleteMapping("/{storeId}/images/{id}")
    @Operation(summary = "사진 삭제")
    public ApiResult<Void> delete(@PathVariable long storeId, @PathVariable long id,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(storeId, id, userInfo.getId());
        return ApiResult.ok();
    }
}
