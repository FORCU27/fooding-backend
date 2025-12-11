package im.fooding.app.service.common.store;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class StoreViewCacheService {

    private static final String STORE_VIEW_KEY_PREFIX = "store:view:";
    private static final Duration STORE_VIEW_TTL = Duration.ofMinutes(5);

    private final StringRedisTemplate redisTemplate;

    public void addView(Long storeId, String viewerKey) {
        String key = STORE_VIEW_KEY_PREFIX + storeId;
        redisTemplate.opsForSet().add(key, viewerKey);
        redisTemplate.expire(key, STORE_VIEW_TTL);
    }

    public long getViewCount(Long storeId) {
        String key = STORE_VIEW_KEY_PREFIX + storeId;
        Long size = redisTemplate.opsForSet().size(key);
        return size == null ? 0 : size;
    }
}
