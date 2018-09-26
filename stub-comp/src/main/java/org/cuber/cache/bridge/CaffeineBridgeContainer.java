package org.cuber.cache.bridge;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.cuber.cache.CacheDefUtils;
import org.cuber.cache.CommonCache;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.util.RpcUtils;
import org.cuber.stub.vo.StubConfVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;

import java.util.concurrent.ConcurrentHashMap;

@Description("虽然是可以传入Object,但是缓存的key 默认使用String")
public class CaffeineBridgeContainer<T extends StubConfVO> extends CommonCache<T> {

    private ConcurrentHashMap<String, T> settleCaches = new ConcurrentHashMap<>();

    private static Logger logger = LoggerFactory.getLogger(CaffeineBridgeContainer.class);


    public CaffeineBridgeContainer(ICacheBridge<T> bridge, Class<T> tClass) {
        super(bridge);
        cache = Caffeine.newBuilder()
                .build(key -> loadByBridge(key, bridge, tClass));

    }

    private T loadByBridge(String key, ICacheBridge<T> bridge, Class<T> tClass) {
        T searchIns = CacheDefUtils.makeSearchIns(key, loadCacheDef(), tClass);
        T result = null;
        if (searchIns != null) {
            try {
                Resp<T> resp = bridge.loadByKey(new Req<>(searchIns));
                if (RpcUtils.isSuccess(resp)) {
                    result = resp.getResult();
                }
            } catch (Exception e) {
                logger.error("远程调用出错,不可能发生", e);
            }

        }
        return result;
    }

}
