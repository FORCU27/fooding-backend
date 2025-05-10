package im.fooding.core.support.dummy;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.model.waiting.WaitingUserPolicyAgreement;

public class WaitingUserDummy {

    public static WaitingUser create(Store store) {
        WaitingUserPolicyAgreement policyAgreement = WaitingUserPolicyAgreement.builder()
                .termsAgreed(true)
                .privacyPolicyAgreed(true)
                .thirdPartyAgreed(true)
                .marketingConsent(true)
                .build();

        return WaitingUser.builder()
                .store(store)
                .name("name")
                .phoneNumber("01012345678")
                .policyAgreement(policyAgreement)
                .build();
    }

    public static WaitingUser createWithPhoneNumber(Store store, String phoneNumber) {
        WaitingUserPolicyAgreement policyAgreement = WaitingUserPolicyAgreement.builder()
                .termsAgreed(true)
                .privacyPolicyAgreed(true)
                .thirdPartyAgreed(true)
                .marketingConsent(true)
                .build();

        return WaitingUser.builder()
                .store(store)
                .name("name")
                .phoneNumber(phoneNumber)
                .policyAgreement(policyAgreement)
                .build();
    }
}
