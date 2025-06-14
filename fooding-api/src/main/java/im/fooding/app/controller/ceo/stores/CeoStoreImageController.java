package im.fooding.app.controller.ceo.stores;

import im.fooding.app.dto.request.ceo.store.CeoCreateStoreImageRequest;
import im.fooding.app.dto.request.ceo.store.CeoSearchStoreImageRequest;
import im.fooding.app.dto.request.ceo.store.CeoUpdateStoreImageRequest;
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
@RequestMapping("/ceo/store-images")
@Tag(name = "CeoStoreImageController", description = "Ceo Store 사진 컨트롤러")
public class CeoStoreImageController {
    private final CeoStoreImageService service;

    @GetMapping
    @Operation(summary = "사진 리스트 조회")
    public ApiResult<PageResponse<CeoStoreImageResponse>> list(@Valid CeoSearchStoreImageRequest search, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(search, userInfo.getId()));
    }

    @PostMapping
    @Operation(summary = "사진 등록")
    public ApiResult<Long> create(@RequestBody @Valid CeoCreateStoreImageRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.create(request, userInfo.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "사진 수정")
    public ApiResult<Void> update(@PathVariable Long id,
                                  @RequestBody @Valid CeoUpdateStoreImageRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        service.update(id, request, userInfo.getId());
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "사진 삭제")
    public ApiResult<Void> delete(@PathVariable Long id,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
