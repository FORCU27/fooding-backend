package im.fooding.core.repository.store.view;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisStoreViewRepository implements StoreViewRepository {

    private static final String STORE_VIEW_KEY_PREFIX = "store:view:";
    private static final Duration STORE_VIEW_TTL = Duration.ofMinutes(5);

    private final StringRedisTemplate redisTemplate;

    @Override
    public long addViewAndGetCount(long storeId, long viewerId) {
        addView(storeId, "user:" + viewerId);
        return getViewCount(storeId);
    }

    @Override
    public long addUnknownViewAndGetCount(long storeId) {
        addView(storeId, "unknown:" + LocalDateTime.now());
        return getViewCount(storeId);
    }

    private void addView(long storeId, String viewerKey) {
        String key = STORE_VIEW_KEY_PREFIX + storeId;
        redisTemplate.opsForSet().add(key, viewerKey);
        redisTemplate.expire(key, STORE_VIEW_TTL);
    }

    private long getViewCount(long storeId) {
        String key = STORE_VIEW_KEY_PREFIX + storeId;
        Long size = redisTemplate.opsForSet().size(key);
        return size == null ? 0 : size;
    }
}
