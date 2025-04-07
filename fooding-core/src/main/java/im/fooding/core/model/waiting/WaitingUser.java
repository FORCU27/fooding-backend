package im.fooding.core.model.waiting;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

// todo: Store 객체 추가
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class WaitingUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private Store store;

    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean termsAgreed;

    @Column(nullable = false)
    private boolean privacyPolicyAgreed;

    @Column(nullable = false)
    private boolean thirdPartyAgreed;

    @Column(nullable = false)
    private boolean marketingConsent;

    @Column(nullable = false)
    private int count;

//    @Builder
//    public WaitingUser(Store store, String name, String phoneNumber, boolean termsAgreed, boolean privacyPolicyAgreed, boolean thirdPartyAgreed, boolean marketingConsent) {
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.termsAgreed = termsAgreed;
//        this.privacyPolicyAgreed = privacyPolicyAgreed;
//        this.thirdPartyAgreed = thirdPartyAgreed;
//        this.marketingConsent = marketingConsent;
//        this.count = 0;
//    }

    public void visitStore() {
        count++;
    }
}
