package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
import lombok.Builder;

@Builder
public record WaitingUserUpdateRequest(

        Long id,
        Store store,
        String name,
        String phoneNumber,
        Boolean termsAgreed,
        Boolean privacyPolicyAgreed,
        Boolean thirdPartyAgreed,
        Boolean marketingConsent,
        Integer count
) {
}
