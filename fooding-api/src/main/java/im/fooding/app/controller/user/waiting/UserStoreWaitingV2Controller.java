package im.fooding.app.controller.user.waiting;

import im.fooding.app.dto.request.user.waiting.UserStoreWaitingRegisterRequestV2;
import im.fooding.app.dto.response.user.waiting.UserStoreWaitingCreateResponseV2;
import im.fooding.app.service.user.waiting.UserStoreWaitingV2Service;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/user/store-waitings")
@Tag(name = "UserStoreWaitingController V2", description = "유저 스토어 웨이팅 컨트롤러")
public class UserStoreWaitingV2Controller {

    private final UserStoreWaitingV2Service userWaitingServiceV2;

    @PostMapping
    @Operation(summary = "온라인 웨이팅 접수")
    public ApiResult<UserStoreWaitingCreateResponseV2> registerStoreWaiting(
            @RequestBody UserStoreWaitingRegisterRequestV2 request, @AuthenticationPrincipal UserInfo userInfo

    ) {
        return ApiResult.ok(userWaitingServiceV2.registerStoreWaiting(request, userInfo.getId()));
    }
}
