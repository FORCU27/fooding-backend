package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.waiting.WaitingStatus;
import lombok.Builder;

@Builder
public record WaitingSettingUpdateRequest(
        Long id,
        StoreService storeService,
        String label,
        Integer minimumCapacity,
        Integer maximumCapacity,
        Integer estimatedWaitingTimeMinutes,
        Boolean isActive,
        Integer entryTimeLimitMinutes,
        WaitingStatus status
) {
}
