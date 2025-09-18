package im.fooding.app.controller.admin.stores;

import im.fooding.app.dto.request.admin.store.AdminCreateStoreImageRequest;
import im.fooding.app.dto.request.admin.store.AdminSearchStoreImageRequest;
import im.fooding.app.dto.request.admin.store.AdminUpdateStoreImageMainRequest;
import im.fooding.app.dto.request.admin.store.AdminUpdateStoreImageRequest;
import im.fooding.app.dto.response.admin.store.AdminStoreImageResponse;
import im.fooding.app.service.admin.store.AdminStoreImageService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/stores")
@Tag(name = "AdminStoreImageController", description = "관리자 가게 이미지 컨트롤러")
public class AdminStoreImageController {
    
    private final AdminStoreImageService service;

    @GetMapping("/{storeId}/images")
    @Operation(summary = "가게 이미지 목록 조회")
    public ApiResult<PageResponse<AdminStoreImageResponse>> list(
            @PathVariable Long storeId, 
            @ModelAttribute AdminSearchStoreImageRequest search) {
        return ApiResult.ok(service.list(storeId, search));
    }

    @GetMapping("/images/{id}")
    @Operation(summary = "가게 이미지 상세 조회")
    public ApiResult<AdminStoreImageResponse> retrieve(@PathVariable Long id) {
        return ApiResult.ok(service.retrieve(id));
    }

    @PostMapping("/images")
    @Operation(summary = "가게 이미지 생성")
    public ApiResult<Long> create(@RequestBody @Valid AdminCreateStoreImageRequest request) {
        Long id = service.create(request);
        return ApiResult.ok(id);
    }

    @PutMapping("/images/{id}")
    @Operation(summary = "가게 이미지 수정")
    public ApiResult<Void> update(
            @PathVariable Long id, 
            @RequestBody @Valid AdminUpdateStoreImageRequest request) {
        service.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/images/{id}")
    @Operation(summary = "가게 이미지 삭제")
    public ApiResult<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResult.ok();
    }

    @PostMapping("/images/{id}/main")
    @Operation(summary = "가게 이미지 대표이미지 설정")
    public ApiResult<Void> updateMain(@PathVariable Long id, @RequestBody @Valid AdminUpdateStoreImageMainRequest request) {
        service.updateMain(id, request);
        return ApiResult.ok();
    }
}
