package im.fooding.app.controller.ceo.stores;

import im.fooding.app.dto.response.ceo.store.CeoStoreBookmarkResponse;
import im.fooding.app.service.ceo.store.CeoStoreBookmarkService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
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
@Tag(name = "CeoStoreBookmarkController", description = "Ceo Store 단골 컨트롤러")
public class CeoStoreBookmarkController {
    private final CeoStoreBookmarkService service;

    @GetMapping("/{storeId}/bookmarks")
    @Operation(summary = "단골 조회")
    public ApiResult<PageResponse<CeoStoreBookmarkResponse>> list(@PathVariable Long storeId, @Valid BasicSearch search, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(storeId, search, userInfo.getId()));
    }

    @DeleteMapping("/{storeId}/bookmarks/{id}")
    @Operation(summary = "단골 삭제")
    public ApiResult<Void> delete(@PathVariable Long storeId, @PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(storeId, id, userInfo.getId());
        return ApiResult.ok();
    }
}
