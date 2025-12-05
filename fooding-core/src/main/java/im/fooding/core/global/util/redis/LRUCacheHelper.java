package im.fooding.core.global.util.redis;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LRUCacheHelper {
    @Qualifier("contentCacheManager")
    private final CacheManager contentCacheManager;
    private final RedisTemplate<String, Object> redisTemplate;

    // 캐시별 최대 크기 설정
    private static final Map<String, Integer> CACHE_SIZE_LIMITS = Map.of(
            "UserNewStore", 10
    );

    public <T> void putWithLimit(String cacheName, Object key, T value) {
        Cache cache = contentCacheManager.getCache(cacheName);
        if (cache == null) throw new ApiException(ErrorCode.REDIS_MANAGER_GET_ERROR);

        // 해당 캐시에 제한이 있는지 확인
        Integer maxSize = CACHE_SIZE_LIMITS.get(cacheName);

        if (maxSize != null) {
            // 제한이 있는 경우에만 크기 체크
            Set<String> keys = redisTemplate.keys(cacheName + "::*");

            if (keys != null && keys.size() >= maxSize) {
                // 가장 오래된 키 삭제
                String oldestKey = findOldestKey(cacheName, keys);
                if (oldestKey != null) {
                    String keyOnly = oldestKey.substring(cacheName.length() + 2);
                    cache.evict(keyOnly);
                }
            }
        }

        // 새 데이터 저장
        cache.put(key, value);
    }

    private String findOldestKey(String cacheName, Set<String> keys) {
        // TTL이 가장 긴 것 = 가장 오래 전에 생성된 것
        return keys.stream()
                .min((k1, k2) -> {
                    Long ttl1 = redisTemplate.getExpire(k1);
                    Long ttl2 = redisTemplate.getExpire(k2);
                    return Long.compare(ttl2 == null ? 0 : ttl2, ttl1 == null ? 0 : ttl1);
                })
                .orElse(null);
    }
}
