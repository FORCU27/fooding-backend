package im.fooding.core.global.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse {
    @Schema(description = "access_token")
    private String accessToken;

    @Schema(description = "access_token 만료시간")
    private Long expiredIn;

    @Schema(description = "refresh_token")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;

    @Schema(description = "refresh_token 만료시간")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long refreshExpiredIn;

    @Builder
    private TokenResponse(String accessToken, Long expiredIn, String refreshToken, Long refreshExpiredIn) {
        this.accessToken = accessToken;
        this.expiredIn = expiredIn;
        this.refreshToken = refreshToken;
        this.refreshExpiredIn = refreshExpiredIn;
    }
}
