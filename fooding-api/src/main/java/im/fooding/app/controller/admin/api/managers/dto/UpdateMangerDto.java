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

    @NotBlank
    @Size(max = 15)
    @Schema(description = "핸드폰번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "프로필 이미지 파일", example = "https://example.com/images/profile.jpg")
    private String profileImage;
}
