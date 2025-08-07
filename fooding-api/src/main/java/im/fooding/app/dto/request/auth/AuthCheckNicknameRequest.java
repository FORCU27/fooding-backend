package im.fooding.app.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthCheckNicknameRequest {
    @NotBlank
    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;
}
