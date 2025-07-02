package im.fooding.app.controller.admin.stores;

import im.fooding.app.dto.response.admin.store.AdminStoreBookmarkResponse;
import im.fooding.app.service.admin.store.AdminStoreBookmarkService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
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
@Tag(name = "AdminStoreBookmarkController", description = "관리자 가게 단골 컨트롤러")
public class AdminStoreBookmarkController {
    private final AdminStoreBookmarkService service;

    @GetMapping("/{storeId}/bookmarks")
    @Operation(summary = "단골 전체 조회")
    public ApiResult<PageResponse<AdminStoreBookmarkResponse>> list(@PathVariable Long storeId, @Valid BasicSearch search) {
        return ApiResult.ok(service.list(storeId, search));
    }

    @DeleteMapping("/{storeId}/bookmarks/{id}")
    @Operation(summary = "단골 삭제")
    public ApiResult<Void> delete(@PathVariable Long storeId, @PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(storeId, id, userInfo.getId());
        return ApiResult.ok();
    }
}
