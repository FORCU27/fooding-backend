package im.fooding.core.global.feign.dto.naver;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverUserProfile {
    private String id;
    private String nickname;
    private String name;
    private String email;
    private String mobile;
}
