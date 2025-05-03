package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
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
        return WaitingUser.builder()
                .store(store)
                .name(name)
                .phoneNumber(phoneNumber)
                .termsAgreed(termsAgreed)
                .privacyPolicyAgreed(privacyPolicyAgreed)
                .thirdPartyAgreed(thirdPartyAgreed)
                .marketingConsent(marketingConsent)
                .build();
    }
}
