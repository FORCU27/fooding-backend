package im.fooding.app.dto.response.admin.user;

import im.fooding.core.model.user.AuthProvider;
import im.fooding.core.model.user.Gender;
import im.fooding.core.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminUserResponse {
    @Schema(description = "유저 ID", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "이메일", example = "user@example.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "닉네임", example = "홍길동", requiredMode = RequiredMode.REQUIRED)
    private String nickname;

    @Schema(description = "전화번호", example = "010-1234-5678", requiredMode = RequiredMode.NOT_REQUIRED)
    private String phoneNumber;

    @Schema(description = "성별(MALE, FEMALE, OTHER, NONE)", example = "MALE", requiredMode = RequiredMode.REQUIRED)
    private Gender gender;

    @Schema(description = "로그인방식(GOOGLE, KAKAO, APPLE, NAVER, FOODING)", example = "GOOGLE", requiredMode = RequiredMode.REQUIRED)
    private AuthProvider provider;

    @Schema(description = "이용약관 동의 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private boolean termsAgreed;

    @Schema(description = "개인정보처리방침 동의 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private boolean privacyPolicyAgreed;

    @Schema(description = "마케팅 수신 동의 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private boolean marketingConsent;

    @Schema(description = "서비스 푸쉬 동의 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private boolean pushAgreed;

    @Schema(description = "가입일시", example = "2024-03-20 14:30:00", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime createdAt;

    @Schema(description = "마지막 로그인 일시", example = "2024-03-21 09:15:00", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime lastLoggedInAt;

    @Builder
    private AdminUserResponse(Long id, String email, String nickname, String phoneNumber, Gender gender, AuthProvider provider,
                              boolean termsAgreed, boolean privacyPolicyAgreed, boolean marketingConsent, boolean pushAgreed,
                              LocalDateTime createdAt, LocalDateTime lastLoggedInAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.provider = provider;
        this.termsAgreed = termsAgreed;
        this.privacyPolicyAgreed = privacyPolicyAgreed;
        this.marketingConsent = marketingConsent;
        this.pushAgreed = pushAgreed;
        this.createdAt = createdAt;
        this.lastLoggedInAt = lastLoggedInAt;
    }

    public static AdminUserResponse from(User user) {
        return AdminUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .provider(user.getProvider())
                .termsAgreed(user.isTermsAgreed())
                .privacyPolicyAgreed(user.isPrivacyPolicyAgreed())
                .marketingConsent(user.isMarketingConsent())
                .pushAgreed(user.isPushAgreed())
                .createdAt(user.getCreatedAt())
                .lastLoggedInAt(user.getLastLoggedInAt())
                .build();
    }
}
