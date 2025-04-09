package im.fooding.core.global.feign.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoTokenResponse {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private Long refreshTokenExpiresIn;

    public static KakaoTokenResponse fail() {
        return new KakaoTokenResponse(null, null);
    }

    private KakaoTokenResponse(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
