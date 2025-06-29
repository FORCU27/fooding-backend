package im.fooding.app.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthRefreshTokenRequest {
    @NotBlank
    @Schema(description = "리프레시 토큰", example = "eyJ0eXAiOiJKV1QiL....")
    private String refreshToken;
}
