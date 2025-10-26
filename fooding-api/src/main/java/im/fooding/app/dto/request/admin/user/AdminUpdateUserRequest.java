package im.fooding.app.dto.request.admin.user;

import im.fooding.core.model.user.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUpdateUserRequest {
    @NotBlank
    @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하로 입력해주세요.")
    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @NotNull
    @Schema(description = "성별(MALE, FEMALE, OTHER, NONE)", example = "MALE")
    private Gender gender;

    @Schema(description = "주소", example = "서울특별시 마포구")
    private String address;

    @Schema(description = "주소 상세", example = "마포빌딩 2층")
    private String addressDetail;

    public AdminUpdateUserRequest(String nickname, String phoneNumber, Gender gender, String address, String addressDetail) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
        this.addressDetail = addressDetail;
    }
}
