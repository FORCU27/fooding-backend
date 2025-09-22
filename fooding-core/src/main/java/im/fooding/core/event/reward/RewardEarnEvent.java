package im.fooding.core.event.reward;

public record RewardEarnEvent(
        String phoneNumber,
        String storeName,
        int point
) {
}
