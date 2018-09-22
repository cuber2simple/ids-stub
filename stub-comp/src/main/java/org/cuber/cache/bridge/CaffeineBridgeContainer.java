package org.cuber.cache.bridge;

import com.github.benmanes.caffeine.cache.CacheWriter;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.apache.commons.collections4.CollectionUtils;
import org.cuber.cache.CacheDefUtils;
import org.cuber.cache.CommonCache;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.util.RpcUtils;
import org.cuber.stub.vo.StubConfVO;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Description("虽然是可以传入Object,但是缓存的key 默认使用String")
public class CaffeineBridgeContainer<T extends StubConfVO> extends CommonCache<T> {

    private ConcurrentHashMap<String, T> settleCaches = new ConcurrentHashMap<>();

    public CaffeineBridgeContainer(ICacheBridge<T> bridge, Class<T> tClass) {
        super(bridge);
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
                .build(key -> loadByBridge(key, bridge, tClass));

    }

    private T loadByBridge(String key, ICacheBridge<T> bridge, Class<T> tClass) {
        T searchIns = CacheDefUtils.makeSearchIns(key, loadCacheDef(), tClass);
        T result = null;
        if (searchIns != null) {
            Resp<T> resp = bridge.loadByKey(new Req<>(searchIns));
            if (RpcUtils.isSuccess(resp)) {
                result = resp.getResult();
            }
        }
        return result;
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

}
