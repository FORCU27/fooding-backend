package im.fooding.core.global.feign.dto.naver;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NaverTokenResponse {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String error;
    private String errorDescription;

    public static NaverTokenResponse fail() {
        return new NaverTokenResponse(null, null);
    }

    private NaverTokenResponse(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
