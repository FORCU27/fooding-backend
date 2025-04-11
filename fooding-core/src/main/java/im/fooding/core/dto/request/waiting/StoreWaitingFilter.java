package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.waiting.StoreWaitingStatus;
import lombok.Builder;

@Builder
public record StoreWaitingFilter(
        Long storeId,
        StoreWaitingStatus status
) {
}
