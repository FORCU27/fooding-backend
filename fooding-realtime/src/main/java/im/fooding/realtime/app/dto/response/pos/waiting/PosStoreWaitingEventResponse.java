package im.fooding.realtime.app.dto.response.pos.waiting;

import im.fooding.core.event.waiting.StoreWaitingEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PosStoreWaitingEventResponse(

        @Schema(description = "가게 ID", example = "1")
        long storeId,

        @Schema(description = "가게 웨이팅 ID", example = "1")
        long storeWaitingId,

        @Schema(description = "이벤트 타입(CREATED, UPDATED, DELETED)", example = "CREATED")
        StoreWaitingEvent.Type eventType
) {

    public static PosStoreWaitingEventResponse from(StoreWaitingEvent event) {
        return PosStoreWaitingEventResponse.builder()
                .storeId(event.storeId())
                .storeWaitingId(event.storeWaitingId())
                .eventType(event.type())
                .build();
    }
}
