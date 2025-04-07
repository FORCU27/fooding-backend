package im.fooding.app.controller.user.auth;

import im.fooding.app.dto.request.user.auth.UserLoginRequest;
import im.fooding.app.service.user.auth.UserAuthApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.jwt.dto.TokenResponse;
import im.fooding.core.model.user.AuthProvider;
import im.fooding.core.model.user.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/auth")
@Tag(name = "UserAuthController", description = "유저 소셜 로그인 컨트롤러")
public class UserAuthController {
    private final UserAuthApplicationService service;

    @PostMapping("/login")
    @Operation(summary = "소셜 로그인", description = "소셜 로그인 처리 후 토큰 응답")
    public ApiResult<TokenResponse> login(@RequestBody @Valid UserLoginRequest requestDto) {
        return ApiResult.ok(service.login(requestDto.getProvider(), requestDto.getToken(), Role.USER));
    }

    @GetMapping("/google/token")
    @Operation(summary = "테스트시 토큰 받기 위한 웹훅 url", hidden = true)
    public ApiResult<TokenResponse> googleTest(@RequestParam("code") String code) {
        return ApiResult.ok(service.login(AuthProvider.GOOGLE, code, Role.USER));
    }

    @GetMapping("/kakao/token")
    @Operation(summary = "테스트시 토큰 받기 위한 웹훅 url", hidden = true)
    public ApiResult<TokenResponse> kakaoTest(@RequestParam("code") String code) {
        return ApiResult.ok(service.login(AuthProvider.KAKAO, code, Role.USER));
    }

    @GetMapping("/naver/token")
    @Operation(summary = "테스트시 토큰 받기 위한 웹훅 url", hidden = true)
    public ApiResult<TokenResponse> naverTest(@RequestParam("code") String code) {
        return ApiResult.ok(service.login(AuthProvider.NAVER, code, Role.USER));
    }

    @GetMapping("/apple/token")
    @Operation(summary = "테스트시 토큰 받기 위한 웹훅 url", hidden = true)
    public ApiResult<TokenResponse> appleTest(@RequestParam("code") String code) {
        return ApiResult.ok(service.login(AuthProvider.APPLE, code, Role.USER));
    }
}
