package im.fooding.app.publisher.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SseEventPublisher {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 주어진 채널에 페이로드를 JSON으로 변환하여 발행
     * @param channel 발행할 Redis 채널 이름
     * @param payload 발행할 객체 (JSON으로 직렬화됨)
     */
    public void publish(String channel, Object payload) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            redisTemplate.convertAndSend(channel, jsonPayload);
            log.info("채널 {}에 이벤트 발행: {}", channel, jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("채널 {}의 이벤트 페이로드 직렬화 실패: {}", channel, payload, e);
        }
    }
}
