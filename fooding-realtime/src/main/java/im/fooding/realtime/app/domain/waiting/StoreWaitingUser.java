package im.fooding.realtime.app.domain.waiting;

import im.fooding.realtime.app.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("waiting_user")
@Getter
public class StoreWaitingUser extends BaseEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("store_id")
    private Long storeId;

    @Column("name")
    private String name;

    @Column("phone_number")
    private String phoneNumber;

    @Column("terms_agreed")
    private boolean termsAgreed;

    @Column("privacy_policy_agreed")
    private boolean privacyPolicyAgreed;

    @Column("third_party_agreed")
    private boolean thirdPartyAgreed;

    @Column("marketing_consent")
    private boolean marketingConsent;

    @Column("count")
    private int count = 0;

    @Builder
    public StoreWaitingUser(
            Long id,
            Long storeId,
            String name,
            String phoneNumber,
            boolean termsAgreed,
            boolean privacyPolicyAgreed,
            boolean thirdPartyAgreed,
            boolean marketingConsent,
            int count
    ) {
        this.id = id;
        this.storeId = storeId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.termsAgreed = termsAgreed;
        this.privacyPolicyAgreed = privacyPolicyAgreed;
        this.thirdPartyAgreed = thirdPartyAgreed;
        this.marketingConsent = marketingConsent;
        this.count = count;
    }
}
