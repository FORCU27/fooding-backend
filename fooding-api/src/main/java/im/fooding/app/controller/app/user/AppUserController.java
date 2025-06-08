package im.fooding.app.controller.app.user;


import im.fooding.app.dto.response.app.user.AppUserResponse;
import im.fooding.app.service.app.user.AppUserService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
@Tag(name = "AppUserController", description = "[APP] 사용자 관리 컨트롤러")
@Slf4j
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    @Operation(summary = "유저 정보 조회")
    ApiResult<AppUserResponse> getUser(
            @AuthenticationPrincipal UserInfo userInfo
    ) {
        return ApiResult.ok(appUserService.getUser(userInfo.getId()));
    }
}
