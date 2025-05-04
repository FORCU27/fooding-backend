package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.model.waiting.WaitingUserPolicyAgreement;
import lombok.Builder;

@Builder
public record WaitingUserCreateRequest(

        Store store,
        String name,
        String phoneNumber,
        Boolean termsAgreed,
        Boolean privacyPolicyAgreed,
        Boolean thirdPartyAgreed,
        Boolean marketingConsent,
        Integer count
) {

    public WaitingUser toWaitingUser() {
        WaitingUserPolicyAgreement policyAgreement = WaitingUserPolicyAgreement.builder()
                .termsAgreed(termsAgreed)
                .privacyPolicyAgreed(privacyPolicyAgreed)
                .thirdPartyAgreed(thirdPartyAgreed)
                .build();

        return WaitingUser.builder()
                .store(store)
                .name(name)
                .phoneNumber(phoneNumber)
                .policyAgreement(policyAgreement)
                .count(count)
                .build();
    }
}
