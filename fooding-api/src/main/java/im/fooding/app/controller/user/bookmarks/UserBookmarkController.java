package im.fooding.app.controller.user.bookmarks;

import im.fooding.app.dto.request.user.bookmark.UserAddBookmarkRequest;
import im.fooding.app.dto.response.user.bookmark.UserBookmarkResponse;
import im.fooding.app.service.user.store.UserBookmarkService;
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
@RequestMapping("/user/bookmarks")
@Tag(name = "UserBookmarkController", description = "유저 단골 컨트롤러")
public class UserBookmarkController {
    private final UserBookmarkService service;

    @GetMapping
    @Operation(summary = "단골 가게 조회")
    public ApiResult<PageResponse<UserBookmarkResponse>> list(@AuthenticationPrincipal UserInfo userInfo, @Valid BasicSearch search) {
        return ApiResult.ok(service.list(userInfo.getId(), search));
    }

    @PostMapping
    @Operation(summary = "단골 가게 추가")
    public ApiResult<Long> add(@RequestBody @Valid UserAddBookmarkRequest request, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.add(request.getStoreId(), userInfo.getId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "단골 가게 삭제")
    public ApiResult<Void> list(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
