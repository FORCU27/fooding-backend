package im.fooding.core.global.feign.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class KakaoUserResponse {
    @JsonProperty("kakao_account")
    private KakaoUserProfile user;

    private String id;

    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    public static KakaoUserResponse fail() {
        return null;
    }
}
