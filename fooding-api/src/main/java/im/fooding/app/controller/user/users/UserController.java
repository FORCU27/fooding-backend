package im.fooding.app.controller.user.users;

import im.fooding.app.dto.response.admin.manager.AdminManagerResponse;
import im.fooding.app.service.user.user.UserApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/users")
@Tag(name = "UserController", description = "유저 정보 컨트롤러")
public class UserController {
    private final UserApplicationService service;

    @GetMapping("/me")
    @Operation(summary = "로그인한 정보")
    public ApiResult<AdminManagerResponse> me(@AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(userInfo.getId()));
    }
}
