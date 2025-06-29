package im.fooding.core.global.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse {
    @Schema(description = "access_token", requiredMode = RequiredMode.REQUIRED)
    private String accessToken;

    @Schema(description = "access_token 만료시간", requiredMode = RequiredMode.REQUIRED)
    private Long expiredIn;

    @Schema(description = "refresh_token", requiredMode = RequiredMode.REQUIRED)
    private String refreshToken;

    @Schema(description = "refresh_token 만료시간", requiredMode = RequiredMode.REQUIRED)
    private Long refreshExpiredIn;

    @Builder
    private TokenResponse(String accessToken, Long expiredIn, String refreshToken, Long refreshExpiredIn) {
        this.accessToken = accessToken;
        this.expiredIn = expiredIn;
        this.refreshToken = refreshToken;
        this.refreshExpiredIn = refreshExpiredIn;
    }
}
