package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.waiting.Waiting;
import lombok.Builder;

@Builder
public record WaitingSettingCreateRequest(

        Waiting waiting,
        String label,
        Integer minimumCapacity,
        Integer maximumCapacity,
        Integer estimatedWaitingTimeMinutes,
        Boolean isActive,
        Integer entryTimeLimitMinutes
) {
}
