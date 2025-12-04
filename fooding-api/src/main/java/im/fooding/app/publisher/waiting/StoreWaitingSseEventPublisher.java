package im.fooding.app.publisher.waiting;

import im.fooding.app.publisher.common.SseEventPublisher;
import im.fooding.core.event.waiting.StoreWaitingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreWaitingSseEventPublisher {

    private static final String CHANNEL_PATTERN = "stores:%d:store_waitings";

    private final SseEventPublisher sseEventPublisher;

    public void publish(StoreWaitingEvent event) {
        String channel = CHANNEL_PATTERN.formatted(event.storeId());

        sseEventPublisher.publish(channel, event);
    }
}
