package im.fooding.app.controller.user.waiting;

import im.fooding.app.dto.request.user.waiting.UserStoreWaitingRegisterRequestV3;
import im.fooding.app.dto.response.user.waiting.UserStoreWaitingCreateResponseV3;
import im.fooding.app.service.user.waiting.UserStoreWaitingV3Service;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v3/user/store-waitings")
@Tag(name = "UserStoreWaitingController V3", description = "유저 스토어 웨이팅 컨트롤러 (대기열 적용)")
public class UserStoreWaitingV3Controller {

    private final UserStoreWaitingV3Service userWaitingServiceV3;

    @PostMapping
    @Operation(summary = "온라인 웨이팅 접수 (대기열)")
    public ApiResult<UserStoreWaitingCreateResponseV3> registerStoreWaiting(
            @RequestBody UserStoreWaitingRegisterRequestV3 request, @AuthenticationPrincipal UserInfo userInfo
    ) {
        return ApiResult.ok(userWaitingServiceV3.registerStoreWaiting(request, userInfo.getId()));
    }
}
