package im.fooding.app.dto.response.auth;

import im.fooding.core.model.user.Gender;
import im.fooding.core.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuthUserResponse {
    @Schema(description = "id", example = "1")
    private long id;

    @Schema(description = "이메일", example = "admin@gmail.com")
    private String email;

    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "휴대폰번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "추천인코드", example = "123213")
    private String referralCode;

    @Schema(description = "프로필 이미지 url", example = "https://..")
    private String profileImage;

    @Schema(description = "로그인 횟수", example = "1")
    private int loginCount;

    @Schema(description = "마지막 로그인 시간", example = "2025-03-15T05:17:04.069")
    private LocalDateTime lastLoggedInAt;

    @Schema(description = "이용약관 동의 여부", example = "true")
    private boolean termsAgreed;

    @Schema(description = "이용약관 동의 날짜", example = "2025-03-15T05:17:04.069")
    private LocalDateTime termsAgreedAt;

    @Schema(description = "개인정보처리방침 동의 여부", example = "true")
    private boolean privacyPolicyAgreed;

    @Schema(description = "개인정보 처리방침 동의 날짜", example = "2025-03-15T05:17:04.069")
    private LocalDateTime privacyPolicyAgreedAt;

    @Schema(description = "마케팅 수신 동의 여부", example = "true")
    private boolean marketingConsent;

    @Schema(description = "마케팅 수신 동의 날짜", example = "2025-03-15T05:17:04.069")
    private LocalDateTime marketingConsentAt;

    @Schema(description = "성별", example = "MALE")
    private Gender gender;

    @Schema(description = "가입일자", example = "2025-03-15T05:17:04.069")
    private LocalDateTime createdAt;

    @Schema(description = "수정일자", example = "2025-03-16T05:17:04.069")
    private LocalDateTime updatedAt;

    @Builder
    private AuthUserResponse(long id, String email, String nickname, String phoneNumber, String referralCode, String profileImage, int loginCount, LocalDateTime lastLoggedInAt, boolean termsAgreed, LocalDateTime termsAgreedAt, boolean privacyPolicyAgreed, LocalDateTime privacyPolicyAgreedAt, boolean marketingConsent, LocalDateTime marketingConsentAt, Gender gender, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.referralCode = referralCode;
        this.profileImage = profileImage;
        this.loginCount = loginCount;
        this.lastLoggedInAt = lastLoggedInAt;
        this.termsAgreed = termsAgreed;
        this.termsAgreedAt = termsAgreedAt;
        this.privacyPolicyAgreed = privacyPolicyAgreed;
        this.privacyPolicyAgreedAt = privacyPolicyAgreedAt;
        this.marketingConsent = marketingConsent;
        this.marketingConsentAt = marketingConsentAt;
        this.gender = gender;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AuthUserResponse of(User user) {
        return AuthUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .referralCode(user.getReferralCode())
                .profileImage(user.getProfileImage())
                .loginCount(user.getLoginCount())
                .lastLoggedInAt(user.getLastLoggedInAt())
                .termsAgreed(user.isTermsAgreed())
                .termsAgreedAt(user.getTermsAgreedAt())
                .privacyPolicyAgreed(user.isPrivacyPolicyAgreed())
                .privacyPolicyAgreedAt(user.getPrivacyPolicyAgreedAt())
                .marketingConsent(user.isMarketingConsent())
                .marketingConsentAt(user.getMarketingConsentAt())
                .gender(user.getGender())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
