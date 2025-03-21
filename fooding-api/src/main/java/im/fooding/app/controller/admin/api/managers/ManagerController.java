package im.fooding.app.controller.admin.api.managers;

import im.fooding.app.controller.admin.api.managers.dto.CreateManagerDto;
import im.fooding.app.controller.admin.api.managers.dto.LoginManagerDto;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import im.fooding.core.global.jwt.dto.TokenResponse;
import im.fooding.core.model.user.User;
import im.fooding.core.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/managers")
@Tag(name = "ManagerController", description = "관리자 매니저 컨트롤러")
@Slf4j
public class ManagerController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "관리자 회원가입")
    public ApiResult<Void> register(@RequestBody @Valid CreateManagerDto createManagerDto) {
        userService.saveManager(createManagerDto.getEmail(), createManagerDto.getPassword());
        return ApiResult.ok();
    }

    @PostMapping("/login")
    @Operation(summary = "관리자 로그인")
    public ApiResult<TokenResponse> login(@Valid @RequestBody LoginManagerDto loginManagerDto) {
        return ApiResult.ok(userService.loginManager(loginManagerDto.getEmail(), loginManagerDto.getPassword()));
    }

    @GetMapping("/me")
    @Operation(summary = "로그인한 정보")
    public ApiResult<User> me(@AuthenticationPrincipal UserInfo userInfo) {
        log.info("userInfo : {}", userInfo);
        return ApiResult.ok(userService.me());
    }
}
