package im.fooding.core.global.feign.dto.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class GoogleUserResponse {
    private String id;
    private String email;
    private boolean verifiedEmail;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;
    private String hd;
}
