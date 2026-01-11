package im.fooding.core.model.store;

public enum StorePaymentMethod {
    LOCAL_CURRENCY_CARD,   // 지역화폐(카드형)
    LOCAL_CURRENCY_CASH,   // 지역화폐(지류형)
    LOCAL_CURRENCY_MOBILE, // 지역화폐(모바일형)
    ZERO_PAY,              // 제로페이
    MOBILE_PAY             // 간편결제
}
