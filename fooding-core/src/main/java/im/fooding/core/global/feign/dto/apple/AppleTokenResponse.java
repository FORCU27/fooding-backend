package im.fooding.core.global.feign.dto.apple;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AppleTokenResponse {
    private String accessToken;
    private Long expiresIn;
    private String idToken;
    private String refreshToken;
    private String tokenType;
}
