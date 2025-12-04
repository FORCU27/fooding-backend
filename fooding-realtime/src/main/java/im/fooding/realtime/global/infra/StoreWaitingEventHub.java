package im.fooding.realtime.global.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import im.fooding.core.event.waiting.StoreWaitingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreWaitingEventHub {

    private static final String CHANNEL_PATTERN = "stores:%d:store_waitings";

    private final RedisMessageListenerContainer redisContainer;
    private final ObjectMapper objectMapper;

    private final Map<Long, Sinks.Many<StoreWaitingEvent>> channelSinks = new ConcurrentHashMap<>();
    private final Map<Long, MessageListener> storeListeners = new ConcurrentHashMap<>();
    private final Map<Long, AtomicInteger> subscriberCounts = new ConcurrentHashMap<>();

    /**
     * 특정 가게 ID에 대한 StoreWaitingEvent 스트림을 제공
     * @param storeId 가게 ID
     * @return StoreWaitingEvent의 Flux
     */
    public Flux<StoreWaitingEvent> subscribe(Long storeId) {
        Sinks.Many<StoreWaitingEvent> sink = channelSinks.computeIfAbsent(storeId, this::createChannelAndListen);

        return sink.asFlux()
            .doOnSubscribe(subscription -> {
                AtomicInteger count = subscriberCounts.computeIfAbsent(storeId, k -> new AtomicInteger(0));
                int newCount = count.incrementAndGet();
                log.info("가게 ID {}에 새로운 클라이언트 구독. 현재 구독자 수: {}", storeId, newCount);
            })
            .doFinally(signalType -> {
                AtomicInteger count = subscriberCounts.get(storeId);
                if (count != null) {
                    int newCount = count.decrementAndGet();
                    if (newCount == 0) {
                        unsubscribe(storeId);
                    }
                }
            });
    }

    private Sinks.Many<StoreWaitingEvent> createChannelAndListen(Long storeId) {
        Sinks.Many<StoreWaitingEvent> sink = Sinks.many().multicast().onBackpressureBuffer();

        MessageListener listener = (message, pattern) -> {
            try {
                StoreWaitingEvent event = objectMapper.readValue(message.getBody(), StoreWaitingEvent.class);
                Sinks.EmitResult result = sink.tryEmitNext(event);

                if (result.isFailure()) {
                    log.warn("가게 ID {}에 대한 이벤트 발행 실패: {}, 결과: {}", storeId, event, result);
                } else {
                    log.debug("가게 ID {}에 대한 이벤트를 발행: {}", storeId, event);
                }
            } catch (IOException e) {
                log.error("Redis 메시지를 StoreWaitingEvent로 역직렬화 실패", e);
            }
        };

        storeListeners.put(storeId, listener);
        redisContainer.addMessageListener(listener, new ChannelTopic(CHANNEL_PATTERN.formatted(storeId)));

        return sink;
    }

    public void unsubscribe(Long storeId) {
        subscriberCounts.remove(storeId);
        Sinks.Many<StoreWaitingEvent> sink = channelSinks.remove(storeId);
        if (sink != null) {
            sink.tryEmitComplete();
            MessageListener listener = storeListeners.remove(storeId);
            if (listener != null) {
                redisContainer.removeMessageListener(listener);
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        for (Long storeId : channelSinks.keySet()) {
            unsubscribe(storeId);
        }
    }
}
