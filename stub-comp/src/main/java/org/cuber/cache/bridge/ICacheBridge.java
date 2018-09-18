package org.cuber.cache.bridge;

import org.cuber.cache.ITopCache;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.vo.StubConfVO;

public interface ICacheBridge<T extends StubConfVO> extends ITopCache<T> {
    Resp<T> loadByKey(Req<T> searchKey);
}
