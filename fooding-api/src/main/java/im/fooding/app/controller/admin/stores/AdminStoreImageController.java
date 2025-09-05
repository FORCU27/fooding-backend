package im.fooding.app.controller.admin.stores;

import im.fooding.app.dto.request.admin.store.image.AdminCreateStoreImageRequest;
import im.fooding.app.dto.request.admin.store.image.AdminSearchStoreImageRequest;
import im.fooding.app.dto.request.admin.store.image.AdminUpdateStoreImageRequest;
import im.fooding.app.dto.response.admin.store.AdminStoreImageResponse;
import im.fooding.app.service.admin.store.AdminStoreImageService;
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
@RequestMapping("/admin/stores")
@Tag(name = "AdminStoreImageController", description = "[ADMIN] 가게 이미지 컨트롤러")
public class AdminStoreImageController {
    private final AdminStoreImageService service;

    @GetMapping("/{storeId}/images")
    @Operation(summary = "사진 리스트 조회")
    public ApiResult<PageResponse<AdminStoreImageResponse>> list(
            @PathVariable long storeId,
            @Valid AdminSearchStoreImageRequest search
    ) {
        return ApiResult.ok(service.list(storeId, search));
    }

    @PostMapping("/{storeId}/images")
    @Operation(summary = "사진 등록")
    public ApiResult<Long> create(
            @PathVariable long storeId,
            @RequestBody @Valid AdminCreateStoreImageRequest request
    ) {
        return ApiResult.ok(service.create(storeId, request));
    }

    @PutMapping("/{storeId}/images/{id}")
    @Operation(summary = "사진 수정")
    public ApiResult<Void> update(
            @PathVariable long storeId,
            @PathVariable long id,
            @RequestBody @Valid AdminUpdateStoreImageRequest request
    ) {
        service.update(storeId, id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{storeId}/images/{id}")
    @Operation(summary = "사진 삭제")
    public ApiResult<Void> delete(
            @PathVariable long storeId,
            @PathVariable long id,
            @AuthenticationPrincipal UserInfo userInfo
    ) {
        service.delete(storeId, id, userInfo.getId());
        return ApiResult.ok();
    }
}

