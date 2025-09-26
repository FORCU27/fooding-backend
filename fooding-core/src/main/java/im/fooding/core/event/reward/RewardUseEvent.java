package im.fooding.core.event.reward;

public record RewardUseEvent(
        long rewardId,
        int usePoint,
        int remainPoint
) {
}
