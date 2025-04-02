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
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column
    private String provider;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(unique = true, length = 20)
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
    public User(Role role, String email, String password, String provider, String nickname, String phoneNumber, boolean termsAgreed, boolean privacyPolicyAgreed, boolean marketingConsent, Gender gender) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.termsAgreed = termsAgreed;
        this.privacyPolicyAgreed = privacyPolicyAgreed;
        this.marketingConsent = marketingConsent;
        this.gender = gender;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
        this.loginCount++;
        this.lastLoggedInAt = LocalDateTime.now();
    }

    public void update(String password, String nickname, String phoneNumber, String profileImage) {
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }
}
