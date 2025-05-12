package im.fooding.core.model.waiting;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class WaitingUserPolicyAgreement {

    @Column(nullable = false)
    private boolean termsAgreed;

    @Column(nullable = false)
    private boolean privacyPolicyAgreed;

    @Column(nullable = false)
    private boolean thirdPartyAgreed;

    @Column(nullable = false)
    private boolean marketingConsent;

    @Builder
    public WaitingUserPolicyAgreement(
            boolean termsAgreed,
            boolean privacyPolicyAgreed,
            boolean thirdPartyAgreed,
            boolean marketingConsent
    ) {
        this.termsAgreed = termsAgreed;
        this.privacyPolicyAgreed = privacyPolicyAgreed;
        this.thirdPartyAgreed = thirdPartyAgreed;
        this.marketingConsent = marketingConsent;
    }
}
