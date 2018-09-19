package org.cuber.stub.conf;

import org.apache.commons.collections4.CollectionUtils;
import org.cuber.basic.facade.bridge.CacheDefBridge;
import org.cuber.cache.CommonCache;
import org.cuber.cache.bridge.CaffeineBridgeContainer;
import org.cuber.cache.bridge.ICacheBridge;
import org.cuber.cache.storage.CaffeineRedisStorage;
import org.cuber.cache.storage.ICacheCarrier;
import org.cuber.stub.basic.CacheDef;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.vo.StubConfVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class CacheFactory {

    private static Logger logger = LoggerFactory.getLogger(CacheFactory.class);

    private static ConcurrentHashMap<Class, CommonCache> bridgeFactory = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private RedisTemplate<String, String> redisTemplate;

    @EventListener
    public void cachePrepare(ApplicationReadyEvent applicationReadyEvent) {
        CacheDefBridge cacheDefBridge = applicationContext.getBean(CacheDefBridge.class);
        if (Objects.nonNull(cacheDefBridge)) {
            Resp<List<CacheDef>> resp = cacheDefBridge.loadCacheByAppName(
                    new Req<>(applicationContext.getApplicationName()));
            if (resp.isSuccess()) {
                List<CacheDef> result = resp.getResult();
                if (CollectionUtils.isNotEmpty(result)) {
                    result.forEach(cacheDef -> consumerCacheDef(cacheDef));
                }
            }
        } else {
            logger.warn("basic服务没启动,或者本服务没有引用basic dubbo服务");
        }
    }

    private void consumerCacheDef(CacheDef def) {
        if (def.isGlobal() && !def.getAppName().equals(applicationContext.getApplicationName())) {
            ICacheBridge bridge = (ICacheBridge) applicationContext.getBean(def.getBridgeClass());
            if (Objects.nonNull(bridge)) {
                CaffeineBridgeContainer caffeineBridgeContainer = new CaffeineBridgeContainer(bridge);
                bridgeFactory.putIfAbsent(def.getCacheInsClass(), caffeineBridgeContainer);
                logger.info("{} bridge缓存已建立", def.getCacheName());
            }
        } else if (def.getAppName().equals(applicationContext.getApplicationName())) {
            ICacheCarrier cacheCarrier = (ICacheCarrier) applicationContext.getBean(def.getCarrierClass());
            if (Objects.nonNull(cacheCarrier)) {
                CaffeineRedisStorage caffeineRedisStorage = new CaffeineRedisStorage(redisTemplate, cacheCarrier);
                bridgeFactory.putIfAbsent(def.getCacheInsClass(), caffeineRedisStorage);
                logger.info("{} 本地缓存已建立", def.getCacheName());
            }
        }
    }

    public static <T extends StubConfVO> T findBySearch(T t, Class<T> tClass) {
        CommonCache commonCache = bridgeFactory.get(tClass);
        T result = null;
        if (Objects.nonNull(commonCache)) {
            result = (T) commonCache.fetchBySearchIns(t);
        } else {
            logger.warn("{} 没有缓存bridge", tClass);
        }
        return result;
    }
}