package im.fooding.app.controller.admin.auth;

import im.fooding.app.dto.request.admin.auth.AdminLoginRequest;
import im.fooding.app.dto.request.admin.manager.AdminCreateManagerRequest;
import im.fooding.app.service.admin.auth.AdminAuthApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.jwt.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/auth")
@Tag(name = "AdminAuthController", description = "관리자 회원가입, 로그인 컨트롤러")
public class AdminAuthController {
    private final AdminAuthApplicationService service;

    @PostMapping("/register")
    @Operation(summary = "관리자 회원가입")
    public ApiResult<Void> register(@RequestBody @Valid AdminCreateManagerRequest adminCreateManagerRequest) {
        service.register(adminCreateManagerRequest);
        return ApiResult.ok();
    }

    @PostMapping("/login")
    @Operation(summary = "관리자 로그인")
    public ApiResult<TokenResponse> login(@Valid @RequestBody AdminLoginRequest loginManagerRequest) {
        return ApiResult.ok(service.login(loginManagerRequest));
    }
}
