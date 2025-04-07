package im.fooding.core.model.user;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "\"user\"")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    @Column(length = 20)
    private String nickname;

    private String phoneNumber;

    @Column(length = 20)
    private String referralCode;

    private String profileImage;

    private String refreshToken;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int loginCount;

    private LocalDateTime lastLoggedInAt;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean termsAgreed;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime termsAgreedAt;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean privacyPolicyAgreed;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime privacyPolicyAgreedAt;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean marketingConsent;

    private LocalDateTime marketingConsentAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Builder
    public User(Role role, String email, String password, AuthProvider provider, String nickname, String phoneNumber, String referralCode, String profileImage, boolean termsAgreed, boolean privacyPolicyAgreed, boolean marketingConsent, Gender gender) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.nickname = nickname;
        this.referralCode = referralCode;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
        this.termsAgreed = termsAgreed;
        this.privacyPolicyAgreed = privacyPolicyAgreed;
        this.marketingConsent = marketingConsent;
        this.gender = gender;
    }

    public void updatedRefreshToken(String updatedRefreshToken) {
        this.refreshToken = updatedRefreshToken;
        this.loginCount++;
        this.lastLoggedInAt = LocalDateTime.now();
    }

    public void update(String nickname, String phoneNumber, String profileImage) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateLastLoggedInAt() {
        this.lastLoggedInAt = LocalDateTime.now();
    }

    public void saveMarketingConsentAt() {
        this.marketingConsentAt = LocalDateTime.now();
    }
}
