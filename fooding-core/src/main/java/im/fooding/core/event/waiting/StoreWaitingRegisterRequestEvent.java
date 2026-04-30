package im.fooding.core.event.waiting;

import lombok.Builder;

@Builder
public record StoreWaitingRegisterRequestEvent(
        String traceId,
        Long userId,
        String name,
        String phoneNumber,
        Long storeId,
        String channel,
        Boolean termsAgreed,
        Boolean privacyPolicyAgreed,
        Boolean thirdPartyAgreed,
        Boolean marketingConsent,
        Integer infantChairCount,
        Integer infantCount,
        Integer adultCount
) {
}
