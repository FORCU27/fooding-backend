package im.fooding.app.controller.user.store;

import im.fooding.app.dto.response.user.store.UserStorePostResponse;
import im.fooding.app.service.user.store.UserStorePostService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ApiResult<List<UserStorePostResponse>> list(@RequestParam Long storeId) {
      List<UserStorePostResponse> storePosts = userStorePostService.list(storeId);
      return ApiResult.ok(storePosts);
    }

    @GetMapping("/{storePostId}")
    @Operation(summary = "유저 가게 소식 상세 조회")
    public ApiResult<UserStorePostResponse> retrieve(@PathVariable Long storePostId) {
      UserStorePostResponse response = userStorePostService.retrieve(storePostId);
      return ApiResult.ok(response);
    }
}
