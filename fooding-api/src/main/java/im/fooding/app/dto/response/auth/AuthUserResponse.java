package im.fooding.app.dto.response.auth;

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
public class AuthUserResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private long id;

    @Schema(description = "이메일", example = "admin@gmail.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "이름", example = "홍길동", requiredMode = RequiredMode.NOT_REQUIRED)
    private String name;

    @Schema(description = "닉네임", example = "홍길동", requiredMode = RequiredMode.REQUIRED)
    private String nickname;

    @Schema(description = "휴대폰번호", example = "010-1234-5678", requiredMode = RequiredMode.NOT_REQUIRED)
    private String phoneNumber;

    @Schema(description = "추천인코드", example = "123213", requiredMode = RequiredMode.NOT_REQUIRED)
    private String referralCode;

    @Schema(description = "프로필 이미지 url", example = "https://..", requiredMode = RequiredMode.NOT_REQUIRED)
    private String profileImage;

    @Schema(description = "로그인 횟수", example = "1", requiredMode = RequiredMode.REQUIRED)
    private int loginCount;

    @Schema(description = "성별", example = "NONE", requiredMode = RequiredMode.REQUIRED)
    private Gender gender;

    @Schema(description = "설명", example = "안녕하세요.", requiredMode = RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "이용약관 동의 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private boolean termsAgreed;

    @Schema(description = "개인정보처리방침 동의 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private boolean privacyPolicyAgreed;

    @Schema(description = "마케팅 수신 동의 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private boolean marketingConsent;

    @Schema(description = "서비스 푸쉬 동의 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private boolean pushAgreed;

    @Schema(description = "마지막 로그인 시간", example = "2025-03-15T05:17:04.069", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime lastLoggedInAt;

    @Schema(description = "가입일자", example = "2025-03-15T05:17:04.069", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime createdAt;

    @Schema(description = "수정일자", example = "2025-03-16T05:17:04.069", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime updatedAt;

    @Builder
    private AuthUserResponse(
            long id, String email, String name, String nickname, String phoneNumber, String referralCode, String profileImage, int loginCount,
            Gender gender, String description, boolean termsAgreed, boolean privacyPolicyAgreed, boolean marketingConsent, boolean pushAgreed,
            LocalDateTime lastLoggedInAt, LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.referralCode = referralCode;
        this.profileImage = profileImage;
        this.loginCount = loginCount;
        this.gender = gender;
        this.description = description;
        this.termsAgreed = termsAgreed;
        this.privacyPolicyAgreed = privacyPolicyAgreed;
        this.marketingConsent = marketingConsent;
        this.pushAgreed = pushAgreed;
        this.lastLoggedInAt = lastLoggedInAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AuthUserResponse of(User user) {
        return AuthUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .referralCode(user.getReferralCode())
                .profileImage(user.getProfileImage())
                .loginCount(user.getLoginCount())
                .gender(user.getGender())
                .description(user.getDescription())
                .termsAgreed(user.isTermsAgreed())
                .privacyPolicyAgreed(user.isPrivacyPolicyAgreed())
                .marketingConsent(user.isMarketingConsent())
                .pushAgreed(user.isPushAgreed())
                .lastLoggedInAt(user.getLastLoggedInAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
