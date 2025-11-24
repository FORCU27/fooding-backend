package im.fooding.app.controller.user.store;

import im.fooding.app.dto.request.user.store.UserCreateStorePostCommentRequest;
import im.fooding.app.dto.request.user.store.UserSearchStorePostCommentRequest;
import im.fooding.app.dto.request.user.store.UserUpdateStorePostCommentRequest;
import im.fooding.app.dto.response.user.store.UserStorePostCommentResponse;
import im.fooding.app.service.user.store.UserStorePostCommentService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/post-comments")
@Tag(name = "UserStorePostCommentController", description = "가게 소식 댓글 컨트롤러")
@Slf4j
public class UserStorePostCommentController {
    private final UserStorePostCommentService service;

    @PostMapping
    @Operation(summary = "가게 소식 댓글 작성")
    public ApiResult<Long> write(@RequestBody UserCreateStorePostCommentRequest request, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.write(request, userInfo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "가게 소식 댓글 수정")
    public ApiResult<Void> update(@PathVariable Long id,
                                  @RequestBody UserUpdateStorePostCommentRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        service.update(id, request, userInfo);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "가게 소식 댓글 삭제")
    public ApiResult<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo);
        return ApiResult.ok();
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "가게 소식 댓글 좋아요")
    public ApiResult<Void> like(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.like(id, userInfo);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}/like")
    @Operation(summary = "가게 소식 댓글 좋아요 취소")
    public ApiResult<Void> unlike(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.unlike(id, userInfo);
        return ApiResult.ok();
    }

    @GetMapping
    @Operation(summary = "가게 소식 리스트 조회")
    public ApiResult<PageResponse<UserStorePostCommentResponse>> list(@Valid UserSearchStorePostCommentRequest search, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(search, userInfo));
    }
}
