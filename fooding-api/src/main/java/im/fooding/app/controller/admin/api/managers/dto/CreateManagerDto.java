package im.fooding.app.controller.admin.api.managers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateManagerDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(max = 50)
    @Schema(description = "아이디", example = "admin")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max = 50)
    @Schema(description = "닉네임", example = "관리자")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max = 20)
    @Schema(description = "비밀번호", example = "1234")
    private String password;
}
