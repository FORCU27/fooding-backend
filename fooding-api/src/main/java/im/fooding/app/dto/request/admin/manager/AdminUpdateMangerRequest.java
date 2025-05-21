package im.fooding.app.dto.request.admin.manager;

import im.fooding.core.model.user.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUpdateMangerRequest {
    @NotBlank
    @Size(max = 50)
    @Schema(description = "닉네임", example = "관리자")
    private String nickname;

    @NotBlank
    @Size(max = 15)
    @Schema(description = "핸드폰번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "성별(MALE, FEMALE, OTHER, NONE)", example = "https://example.com/images/profile.jpg")
    private Gender gender;

    public AdminUpdateMangerRequest(String nickname, String phoneNumber, Gender gender) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}
