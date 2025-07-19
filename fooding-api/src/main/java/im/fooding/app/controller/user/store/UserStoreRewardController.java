package im.fooding.app.controller.user.store;

import im.fooding.app.dto.response.user.reward.UserStoreRewardResponse;
import im.fooding.app.service.user.store.UserStoreRewardService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/stores")
@Tag(name = "UserStoreRewardController", description = "유저 스토어 리워드 컨트롤러")
public class UserStoreRewardController {
    private final UserStoreRewardService service;

    @GetMapping("/{storeId}/rewards")
    @Operation(summary = "스토어 리워드 상품 조회")
    public ApiResult<UserStoreRewardResponse> list(@PathVariable Long storeId, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(storeId, userInfo.getId()));
    }

    @PostMapping("/{storeId}/rewards/{id}")
    @Operation(summary = "스토어 리워드 상품 구매")
    public ApiResult<Void> purchase(@PathVariable Long storeId, @PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.purchase(storeId, id, userInfo.getId());
        return ApiResult.ok();
    }
}
