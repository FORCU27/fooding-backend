package im.fooding.app.dto.request.auth;

import im.fooding.core.model.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCreateRequest {
    @NotBlank
    @Email
    @Size(max = 50)
    @Schema(description = "이메일", example = "admin@gmail.com")
    private String email;

    @NotBlank
    @Size(max = 50)
    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;

    @NotBlank
    @Size(min = 4, max = 20)
    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @NotNull
    @Schema(description = "권한(CEO, ADMIN)", example = "ADMIN")
    private Role role;

    @Builder
    public AuthCreateRequest(String email, String nickname, String password, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
}
