package im.fooding.app.dto.request.waiting;

import lombok.Builder;

@Builder
public record WaitingRegisterServiceRequest(
        long storeId,
        String name,
        String phoneNumber,
        boolean termsAgreed,
        boolean privacyPolicyAgreed,
        boolean thirdPartyAgreed,
        boolean marketingConsent,
        String channel,
        int infantChairCount,
        int infantCount,
        int adultCount
) {
}
