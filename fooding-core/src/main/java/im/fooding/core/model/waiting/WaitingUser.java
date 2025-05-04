package im.fooding.core.model.waiting;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class WaitingUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    private String name;

    private String phoneNumber;

    @Embedded
    private WaitingUserPolicyAgreement policyAgreement;

    @Column(nullable = false)
    private int count = 0;

    @Builder
    public WaitingUser(
            Store store,
            String name,
            String phoneNumber,
            WaitingUserPolicyAgreement policyAgreement,
            int count
    ) {
        this.store = store;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.policyAgreement = policyAgreement;
        this.count = count;
    }

    public void visitStore() {
        count++;
    }

    public Long getStoreId() {
        return store.getId();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void update(
            Store store,
            String name,
            String phoneNumber,
            WaitingUserPolicyAgreement policyAgreement,
            int count
    ) {
        this.store = store;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.policyAgreement = policyAgreement;
        this.count = count;
    }
}
