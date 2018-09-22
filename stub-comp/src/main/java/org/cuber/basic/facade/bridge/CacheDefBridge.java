package org.cuber.basic.facade.bridge;

import org.cuber.cache.bridge.ICacheBridge;
import org.cuber.stub.basic.CacheDef;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.rpc.StubException;

import java.util.List;

public interface CacheDefBridge extends ICacheBridge<CacheDef> {
    /**
     * 根据服务名查找缓存的列表
     * @param req 本服务名
     * @return 缓存列表
     */
    Resp<List<CacheDef>> loadCacheByAppName(Req<String> req) throws StubException;
}
