package im.fooding.app.dto.request.admin.user;

import im.fooding.core.model.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminCreateUserRequest {
    @NotBlank
    @Email
    @Size(max = 50)
    @Schema(description = "이메일", example = "admin@gmail.com")
    private String email;

    @NotBlank
    @Size(min = 2, max = 50)
    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;

    @NotBlank
    @Size(min = 4, max = 20)
    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @NotNull
    @Schema(description = "권한(CEO, ADMIN)", example = "ADMIN")
    private Role role;

    @Builder
    public AdminCreateUserRequest(String email, String nickname, String password, String phoneNumber, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
