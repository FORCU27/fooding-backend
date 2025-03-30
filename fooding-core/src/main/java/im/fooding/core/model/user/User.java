package im.fooding.core.model.user;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String email;

    private String password;

    private String nickname;

    private String mobile;

    private String refreshToken;

    private int loginCount;

    private LocalDateTime lastLoginAt;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean termsAgree;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean privacyAgree;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean marketingAgree;

    @Builder
    public User(Role role, String email, String password, String nickname, String mobile, boolean termsAgree, boolean privacyAgree, boolean marketingAgree) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.mobile = mobile;
        this.termsAgree = termsAgree;
        this.privacyAgree = privacyAgree;
        this.marketingAgree = marketingAgree;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
        this.lastLoginAt = LocalDateTime.now();
        this.loginCount++;
    }

    public void update(String nickname) {
        this.nickname = nickname;
    }
}
