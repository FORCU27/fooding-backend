package im.fooding.app.dto.response.app.user;

import im.fooding.core.model.user.AuthProvider;
import im.fooding.core.model.user.Gender;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record AppUserResponse(

        @Schema(description = "ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "aaa111@mail.com")
        String email,

        @Schema(description = "소셜 로그인 제공자", example = "FOODING")
        AuthProvider provider,

        @Schema(description = "닉네임", example = "usernickname")
        String nickname,

        @Schema(description = "전화번호", example = "010-1234-5678")
        String phoneNumber,

        @Schema(description = "추천인 코드")
        String referralCode,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/images/profile.jpg")
        String profileImage,

        @Schema(description = "로그인 횟수", example = "15")
        int loginCount,

        @Schema(description = "마지막 로그인 일시", example = "2024-01-01T09:00:00")
        LocalDateTime lastLoggedInAt,

        @Schema(description = "이용약관 동의 여부", example = "false")
        boolean termsAgreed,

        @Schema(description = "이용약관 동의 일시", example = "2024-01-01T09:00:00")
        LocalDateTime termsAgreedAt,

        @Schema(description = "개인정보 처리방침 동의 여부", example = "false")
        boolean privacyPolicyAgreed,

        @Schema(description = "개인정보 처리방침 동의 일시", example = "2024-01-01T09:00:00")
        LocalDateTime privacyPolicyAgreedAt,

        @Schema(description = "마케팅 수신 동의 여부", example = "false")
        boolean marketingConsent,

        @Schema(description = "마케팅 수신 동의 일시", example = "2024-01-01T09:00:00")
        LocalDateTime marketingConsentAt,

        @Schema(description = "성별", example = "MALE")
        Gender gender
) {

    public static AppUserResponse from(User user) {
            return new AppUserResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getProvider(),
                    user.getNickname(),
                    user.getPhoneNumber(),
                    user.getReferralCode(),
                    user.getProfileImage(),
                    user.getLoginCount(),
                    user.getLastLoggedInAt(),
                    user.isTermsAgreed(),
                    user.getTermsAgreedAt(),
                    user.isPrivacyPolicyAgreed(),
                    user.getPrivacyPolicyAgreedAt(),
                    user.isMarketingConsent(),
                    user.getMarketingConsentAt(),
                    user.getGender()
            );
    }
}
