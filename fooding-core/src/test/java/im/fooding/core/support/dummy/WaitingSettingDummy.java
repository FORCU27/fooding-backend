package im.fooding.core.support.dummy;

import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingSetting;

public class WaitingSettingDummy {

    public static WaitingSetting create(Waiting waiting, boolean isActive) {
        return WaitingSetting.builder()
                .waiting(waiting)
                .label("label")
                .minimumCapacity(1)
                .maximumCapacity(10)
                .estimatedWaitingTimeMinutes(15)
                .isActive(isActive)
                .entryTimeLimitMinutes(5)
                .build();
    }
}
