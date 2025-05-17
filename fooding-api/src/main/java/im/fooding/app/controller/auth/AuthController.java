package im.fooding.app.controller.auth;

import im.fooding.app.dto.request.auth.*;
import im.fooding.app.dto.response.auth.AuthUserResponse;
import im.fooding.app.service.auth.AuthService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import im.fooding.core.global.jwt.dto.TokenResponse;
import im.fooding.core.model.user.AuthProvider;
import im.fooding.core.model.user.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "로그인, 회원가입 컨트롤러")
public class AuthController {
    private final AuthService service;

    @GetMapping("/me")
    @Operation(summary = "로그인한 정보", description = "로그인한 유저의 정보를 응답한다.")
    public ApiResult<AuthUserResponse> me(@AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(userInfo.getId()));
    }

    @PutMapping("/me")
    @Operation(summary = "로그인한 유저 정보 수정", description = "로그인된 상태에서 정보 수정")
    public ApiResult<Void> update(@RequestBody @Valid AuthUpdateProfileRequest request,
                                         @AuthenticationPrincipal UserInfo userInfo) {
        service.update(userInfo.getId(), request);
        return ApiResult.ok();
    }

    @PatchMapping("/me/profile-image")
    @Operation(summary = "로그인한 유저 프로필 이미지 수정", description = "로그인된 상태에서 프로필 이미지 수정")
    public ApiResult<Void> updateProfileImage(@RequestBody @Valid AuthUpdateProfileImageRequest request,
                                              @AuthenticationPrincipal UserInfo userInfo) {
        service.updateProfileImage(userInfo.getId(), request.getImageId());
        return ApiResult.ok();
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "회원가입 한다.")
    public ApiResult<Void> register(@RequestBody @Valid AuthCreateRequest request) {
        service.register(request);
        return ApiResult.ok();
    }

    @PostMapping("/login")
    @Operation(summary = "일반 로그인", description = "이메일, 패스워드로 일반 로그인 처리 후 토큰 응답")
    public ApiResult<TokenResponse> login(@RequestBody @Valid AuthLoginRequest request) {
        return ApiResult.ok(service.login(request));
    }

    @PostMapping("/social-login")
    @Operation(summary = "소셜 로그인", description = "소셜 로그인 처리 후 토큰 응답")
    public ApiResult<TokenResponse> loginWithSocial(@RequestBody @Valid AuthSocialLoginRequest request) {
        return ApiResult.ok(service.loginWithSocial(request));
    }

    @GetMapping("/google/token")
    @Operation(summary = "테스트시 토큰 받기 위한 웹훅 url", hidden = true)
    public ApiResult<TokenResponse> googleTest(@RequestParam("code") String code) {
        AuthSocialLoginRequest request = AuthSocialLoginRequest.builder()
                .code(code)
                .provider(AuthProvider.GOOGLE)
                .role(Role.USER)
                .redirectUri("http://localhost:8080/auth/google/token")
                .build();
        return ApiResult.ok(service.loginWithSocial(request));
    }

    @GetMapping("/kakao/token")
    @Operation(summary = "테스트시 토큰 받기 위한 웹훅 url", hidden = true)
    public ApiResult<TokenResponse> kakaoTest(@RequestParam("code") String code) {
        AuthSocialLoginRequest request = AuthSocialLoginRequest.builder()
                .code(code)
                .provider(AuthProvider.KAKAO)
                .role(Role.USER)
                .redirectUri("http://localhost:8080/auth/kakao/token")
                .build();
        return ApiResult.ok(service.loginWithSocial(request));
    }

    @GetMapping("/naver/token")
    @Operation(summary = "테스트시 토큰 받기 위한 웹훅 url", hidden = true)
    public ApiResult<TokenResponse> naverTest(@RequestParam("code") String code) {
        AuthSocialLoginRequest request = AuthSocialLoginRequest.builder()
                .code(code)
                .provider(AuthProvider.NAVER)
                .role(Role.USER)
                .redirectUri("http://localhost:8080/auth/naver/token")
                .build();
        return ApiResult.ok(service.loginWithSocial(request));
    }

    @PostMapping("/apple/token")
    @Operation(summary = "테스트시 토큰 받기 위한 웹훅 url", hidden = true)
    public ApiResult<TokenResponse> appleTest(@RequestParam("code") String code) {
        AuthSocialLoginRequest request = AuthSocialLoginRequest.builder()
                .code(code)
                .provider(AuthProvider.APPLE)
                .role(Role.USER)
                .redirectUri("https://localhost.com/auth/apple/token")
                .build();
        return ApiResult.ok(service.loginWithSocial(request));
    }
}
