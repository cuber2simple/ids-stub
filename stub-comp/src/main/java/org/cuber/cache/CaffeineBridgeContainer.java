package org.cuber.cache;

import com.github.benmanes.caffeine.cache.CacheWriter;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.basic.CacheDef;
import org.cuber.stub.vo.StubConfVO;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Description("虽然是可以传入Object,但是缓存的key 默认使用String")
public class CaffeineBridgeContainer<T extends StubConfVO> {

    private LoadingCache<String, T> cache;

    private CacheDef<T> def;

    private GlobalCacheBridge<T> bridge;

    private ConcurrentHashMap<String, T> settleCaches = new ConcurrentHashMap<>();

    public CaffeineBridgeContainer(GlobalCacheBridge<T> bridge) {
        this.bridge = bridge;
        cache = Caffeine.newBuilder()
                .writer(new CacheWriter<String, T>() {
                    @Override
                    public void write(String key, T cacheIns) {
                        String id = cacheIns.getId();
                        if (!settleCaches.containsKey(id)) {
                            settleCaches.putIfAbsent(id, cacheIns);
                            loadOtherField(key, cacheIns);
                        }
                    }

                    @Override
                    public void delete(String key, T cacheIns, RemovalCause cause) {
                        settleCaches.remove(cacheIns.getId());
                        invalidateCache(key, cacheIns);
                    }
                })
                .build(key -> bridge.loadByKey(key));

    }

    public T loadByKey(Object... keys) {
        String key = StringUtils.join(keys, CacheDefUtils.CACHE_FIELD_SPLIT);
        return cache.get(key);
    }

    private void loadOtherField(String key, T cacheIns) {
        List<String> fieldValues = CacheDefUtils.findKeys(cacheIns, loadCacheDef());
        if (CollectionUtils.isNotEmpty(fieldValues)) {
            fieldValues.stream().forEach(fieldValue -> {
                if (!key.equals(fieldValue)) {
                    cache.put(fieldValue, cacheIns);
                }
            });
        }
    }

    private void invalidateCache(String key, T cacheIns) {
        String id = cacheIns.getId();
        if (settleCaches.containsKey(id)) {
            settleCaches.remove(id);
            List<String> fieldValues = CacheDefUtils.findKeys(cacheIns, loadCacheDef());
            if (CollectionUtils.isNotEmpty(fieldValues)) {
                fieldValues.stream().forEach(fieldValue -> {
                    if (!key.equals(fieldValue)) {
                        cache.invalidate(key);
                    }
                });
            }
        }
    }

    private synchronized CacheDef<T> loadCacheDef() {
        if (Objects.isNull(def)) {
            def = bridge.cacheDef();
        }
        return def;
    }

}
