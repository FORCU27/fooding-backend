package im.fooding.app.controller.admin.api.managers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateManagerDto {
    @NotBlank
    @Size(max = 50)
    @Schema(description = "아이디", example = "admin")
    private String email;

    @NotBlank
    @Size(min = 4, max = 20)
    @Schema(description = "비밀번호", example = "1234")
    private String password;
}
