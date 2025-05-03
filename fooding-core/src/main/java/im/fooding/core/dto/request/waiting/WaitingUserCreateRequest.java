package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
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
        return WaitingUser.builder()
                .store(store)
                .name(name)
                .phoneNumber(phoneNumber)
                .termsAgreed(termsAgreed)
                .privacyPolicyAgreed(privacyPolicyAgreed)
                .thirdPartyAgreed(thirdPartyAgreed)
                .marketingConsent(marketingConsent)
                .count(count)
                .build();
    }
}
