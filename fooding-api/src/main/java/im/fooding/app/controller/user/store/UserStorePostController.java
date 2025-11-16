package im.fooding.app.controller.user.store;

import im.fooding.app.dto.request.user.store.UserSearchStorePostRequest;
import im.fooding.app.dto.response.user.store.UserStorePostResponse;
import im.fooding.app.service.user.store.UserStorePostService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/store-posts")
@Tag(name = "UserStorePostController", description = "유저 가게 소식 컨트롤러")
@Slf4j
public class UserStorePostController {
    private final UserStorePostService userStorePostService;

    @GetMapping
    @Operation(summary = "유저 가게 소식 전체 조회")
    public ApiResult<PageResponse<UserStorePostResponse>> list(UserSearchStorePostRequest search, @AuthenticationPrincipal UserInfo userInfo) {
      return ApiResult.ok(userStorePostService.list(search, userInfo));
    }

    @GetMapping("/{storePostId}")
    @Operation(summary = "유저 가게 소식 상세 조회")
    public ApiResult<UserStorePostResponse> retrieve(@PathVariable Long storePostId, @AuthenticationPrincipal UserInfo userInfo) {
      UserStorePostResponse response = userStorePostService.retrieve(storePostId, userInfo);
      return ApiResult.ok(response);
    }

    @PostMapping("/{storePostId}/like")
    @Operation(summary = "유저 가게 소식 좋아요")
    public ApiResult<Void> like(@PathVariable Long storePostId, @AuthenticationPrincipal UserInfo userInfo) {
        userStorePostService.like(storePostId, userInfo);
        return ApiResult.ok();
    }

    @DeleteMapping("/{storePostId}/like")
    @Operation(summary = "유저 가게 소식 좋아요 취소")
    public ApiResult<Void> unlike(@PathVariable Long storePostId, @AuthenticationPrincipal UserInfo userInfo) {
        userStorePostService.unlike(storePostId, userInfo);
        return ApiResult.ok();
    }
}
