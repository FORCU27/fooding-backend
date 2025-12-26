package im.fooding.app.dto.request.auth;

import im.fooding.core.model.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthCreateRequest {
    @NotBlank
    @Email
    @Size(max = 50)
    @Schema(description = "이메일", example = "admin@gmail.com")
    private String email;

    @NotBlank
    @Size(max = 50)
    @Schema(description = "닉네임", example = "재빠른 홍길동")
    private String nickname;

    @NotBlank
    @Size(min = 4, max = 20)
    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @NotNull
    @Schema(description = "권한(CEO, ADMIN)", example = "ADMIN")
    private Role role;

    @NotNull
    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Size(max = 150, message = "자기소개는 최대 150자까지 가능합니다.")
    @Schema(description = "자기소개", example = "안녕하세요")
    private String description;

    @NotBlank
    @Pattern(regexp = "^\\d{11}$")
    @Schema(description = "전화번호", example = "01000000000")
    private String phoneNumber;

    @Schema(description = "추천인코드", example = "123213")
    private String referralCode;

    @Schema(description = "마케팅 수신 동의 여부", example = "false")
    private boolean marketingConsent;
}
