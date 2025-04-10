package im.fooding.core.support.dummy;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;

public class WaitingUserDummy {

    public static WaitingUser createWithPhoneNumber(Store store, String phoneNumber) {
        return WaitingUser.builder()
                .store(store)
                .name("name")
                .phoneNumber(phoneNumber)
                .termsAgreed(true)
                .privacyPolicyAgreed(true)
                .thirdPartyAgreed(true)
                .marketingConsent(true)
                .build();
    }
}
