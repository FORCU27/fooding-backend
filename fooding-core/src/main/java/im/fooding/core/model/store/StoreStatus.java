package im.fooding.core.model.store;

public enum StoreStatus {
    PENDING,        // Under review / 심사중
    APPROVED,       // Operating / 운영중
    REJECTED,       // Application rejected / 승인거부
    SUSPENDED,      // Temporarily suspended / 일시정지
    CLOSED          // Permanently closed / 폐업
}