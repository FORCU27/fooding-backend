package im.fooding.app.dto.request.user.auth;

import im.fooding.core.model.user.AuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequest {
    @NotNull
    @Schema(description = "소셜타입(GOOGLE, APPLE, NAVER, KAKAO)", example = "GOOGLE")
    private AuthProvider provider;

    @NotBlank
    @Schema(description = "access_token", example = "4/0AQlEd8z...")
    private String token;

    @Builder
    private UserLoginRequest(AuthProvider provider, String token) {
        this.provider = provider;
        this.token = token;
    }
}
