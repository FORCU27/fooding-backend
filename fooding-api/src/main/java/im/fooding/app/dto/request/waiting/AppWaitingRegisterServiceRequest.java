package im.fooding.app.dto.request.waiting;

import lombok.Builder;

@Builder
public record AppWaitingRegisterServiceRequest(
        long storeId,
        String name,
        String phoneNumber,
        boolean termsAgreed,
        boolean privacyPolicyAgreed,
        boolean thirdPartyAgreed,
        boolean marketingConsent,
        int infantChairCount,
        int infantCount,
        int adultCount
) {
}
