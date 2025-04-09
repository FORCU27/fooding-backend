package im.fooding.core.global.feign.dto.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleTokenResponse {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String scope;
    private String idToken;

    public static GoogleTokenResponse fail() {
        return new GoogleTokenResponse(null, null);
    }

    private GoogleTokenResponse(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
