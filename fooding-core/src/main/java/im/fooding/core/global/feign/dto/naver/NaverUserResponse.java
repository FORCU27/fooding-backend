package im.fooding.core.global.feign.dto.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NaverUserResponse {
    @JsonProperty("response")
    private NaverUserProfile user;
    private String resultCode;
    private String message;

    public static NaverUserResponse fail() {
        return null;
    }
}
