package im.fooding.app.dto.request.auth;

import im.fooding.core.model.user.AuthProvider;
import im.fooding.core.model.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthSocialLoginRequest {
    @NotNull
    @Schema(description = "소셜타입(GOOGLE, APPLE, NAVER, KAKAO)", example = "GOOGLE")
    private AuthProvider provider;

    @NotBlank
    @Schema(description = "code", example = "4/0AQlEd8z...")
    private String code;

    @NotBlank
    @Schema(description = "redirect url", example = "http://localhost:3000/api/auth/social/callback")
    private String redirectUri;

    @NotNull
    @Schema(description = "로그인 권한(CEO, USER)", example = "USER")
    private Role role;

    @Builder
    public AuthSocialLoginRequest(AuthProvider provider, String code, String redirectUri, Role role) {
        this.provider = provider;
        this.code = code;
        this.redirectUri = redirectUri;
        this.role = role;
    }
}
