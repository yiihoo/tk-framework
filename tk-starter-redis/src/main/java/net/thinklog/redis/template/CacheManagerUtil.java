
package net.thinklog.redis.template;

import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
/**
 * @author DELL
 */
@AllArgsConstructor
public class CacheManagerUtil {

    private final CacheManager cacheManager;

    @SuppressWarnings({"unchecked"})
    public <T> T getCache(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (valueWrapper == null) {
            return null;
        }
        return (T) valueWrapper.get();
    }

    public void putCache(String cacheName, Object key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    public boolean evictCache(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
            return true;
        }
        return false;
    }
}
