package org.cuber.cache.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.cuber.cache.CacheDefUtils;
import org.cuber.cache.CommonCache;
import org.cuber.stub.basic.CacheDef;
import org.cuber.stub.json.JacksonHolder;
import org.cuber.stub.vo.StubConfVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Objects;

public class CaffeineRedisStorage<T extends StubConfVO> extends CommonCache<T> {

    private RedisTemplate<String, String> redisTemplate;

    public CaffeineRedisStorage(RedisTemplate<String, String> redisTemplate, ICacheCarrier<T> carrier) {
        super(carrier);
        this.redisTemplate = redisTemplate;
        def = this.loadCacheDef();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        if (!def.isDurable()) {
            caffeine.expireAfterWrite(Duration.ofMinutes(def.getDurationOfMinutes()));
        }
        cache = caffeine.build(key -> findKeyWithRedisFirst(key, carrier));
    }

    private T findKeyWithRedisFirst(String key, ICacheCarrier<T> carrier) {
        CacheDef<T> cacheDef = loadCacheDef();
        String redisKey = cacheDef.getCacheRedisKey();
        String objectJson = (String) redisTemplate.opsForHash().get(redisKey, key);
        T t = null;
        if (StringUtils.isEmpty(objectJson)) {
            t = JacksonHolder.instance(objectJson, cacheDef.getCacheInsClass());
        }
        if (Objects.isNull(t)) {
            T searchIns = CacheDefUtils.makeSearchIns(key, cacheDef);
            t = carrier.carryByKey(searchIns);
            if (Objects.nonNull(t)) {
                objectJson = JacksonHolder.toJackson(t);
                redisTemplate.opsForHash().put(redisKey, key, objectJson);
            }
        }
        return t;
    }

}