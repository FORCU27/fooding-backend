package im.fooding.app.dto.request.auth;

import im.fooding.core.model.user.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthUpdateProfileRequest {
    @NotBlank
    @Size(max = 50)
    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "핸드폰번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "성별(MALE, FEMALE, OTHER, NONE)", example = "MALE")
    private Gender gender;

    @Schema(description = "추천인코드", example = "123213")
    private String referralCode;

    @Schema(description = "마케팅 수신 동의 여부", example = "true")
    private boolean marketingConsent;

    @Builder
    public AuthUpdateProfileRequest(String nickname, String phoneNumber, Gender gender, String referralCode, boolean marketingConsent) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.referralCode = referralCode;
        this.marketingConsent = marketingConsent;
    }
}
