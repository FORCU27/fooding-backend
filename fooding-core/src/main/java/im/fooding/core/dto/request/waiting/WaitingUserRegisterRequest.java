package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.model.waiting.WaitingUserPolicyAgreement;
import lombok.Builder;

@Builder
public record WaitingUserRegisterRequest(
        Store store,
        String name,
        String phoneNumber,
        boolean termsAgreed,
        boolean privacyPolicyAgreed,
        boolean thirdPartyAgreed,
        boolean marketingConsent
) {

    public WaitingUser toWaitingUser() {
        WaitingUserPolicyAgreement policyAgreement = WaitingUserPolicyAgreement.builder()
                .termsAgreed(termsAgreed)
                .privacyPolicyAgreed(privacyPolicyAgreed)
                .thirdPartyAgreed(thirdPartyAgreed)
                .marketingConsent(marketingConsent)
                .build();

        return WaitingUser.builder()
                .store(store)
                .name(name)
                .phoneNumber(phoneNumber)
                .policyAgreement(policyAgreement)
                .build();
    }
}
