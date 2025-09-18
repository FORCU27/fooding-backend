package im.fooding.core.repository.waiting;

import im.fooding.core.model.waiting.WaitingStatus;
import lombok.Builder;

@Builder
public record WaitingSettingFilter(
        Long storeServiceId,
        WaitingStatus status
) {
}
