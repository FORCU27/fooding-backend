package im.fooding.app.controller.admin.stores;

import im.fooding.app.dto.request.admin.store.AdminStorePostCreateRequest;
import im.fooding.app.dto.request.admin.store.AdminStorePostListRequest;
import im.fooding.app.dto.request.admin.store.AdminStorePostUpdateRequest;
import im.fooding.app.dto.response.admin.store.AdminStorePostResponse;
import im.fooding.app.service.admin.store.AdminStorePostService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/store-posts")
@Tag(name = "AdminStorePostController", description = "[ADMIN] 가게 소식 컨트롤러")
@Slf4j
public class AdminStorePostController {

    private final AdminStorePostService adminStorePostService;

    @PostMapping
    @Operation(summary = "가게 소식 생성")
    public ApiResult<Void> create(
            @RequestBody @Valid AdminStorePostCreateRequest request
    ) {
        adminStorePostService.create(request);
        return ApiResult.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "가게 소식 조회")
    public ApiResult<AdminStorePostResponse> get(
            @PathVariable long id
    ) {
        return ApiResult.ok(adminStorePostService.get(id));
    }

    @GetMapping
    @Operation(summary = "가게 소식 조회(page)")
    public ApiResult<PageResponse<AdminStorePostResponse>> list(
            @Parameter(description = "검색 및 페이징 조건 (storeId 포함)")
            @ModelAttribute AdminStorePostListRequest search
    ) {
        return ApiResult.ok(adminStorePostService.list(search));
    }

    @PutMapping("/{id}")
    @Operation(summary = "가게 소식 수정")
    public ApiResult<Void> update(
            @PathVariable long id,
            @RequestBody AdminStorePostUpdateRequest request,
            @AuthenticationPrincipal UserInfo userInfo
    ) {
        adminStorePostService.update(id, request, userInfo.getId());
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "가게 소식 제거")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable long id
    ) {
        adminStorePostService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
