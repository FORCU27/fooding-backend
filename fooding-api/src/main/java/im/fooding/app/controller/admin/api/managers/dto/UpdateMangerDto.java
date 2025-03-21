package im.fooding.app.controller.admin.api.managers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMangerDto {
    @NotBlank
    @Size(max = 50)
    @Schema(description = "닉네임", example = "관리자")
    private String nickname;
}
