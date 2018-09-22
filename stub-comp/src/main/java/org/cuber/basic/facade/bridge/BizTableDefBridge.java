package org.cuber.basic.facade.bridge;

import org.cuber.cache.bridge.ICacheBridge;
import org.cuber.stub.basic.BizTableDef;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.rpc.StubException;

import java.util.List;

public interface BizTableDefBridge extends ICacheBridge<BizTableDef> {

    /**
     * 根据服务名查找缓存的分表信息
     *
     * @param req 本服务名
     * @return 缓存列表
     */
    Resp<List<BizTableDef>> loadCacheByAppName(Req req) throws StubException;
}
