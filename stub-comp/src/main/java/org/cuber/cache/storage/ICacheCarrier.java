package org.cuber.cache.storage;

import org.cuber.cache.ITopCache;
import org.cuber.stub.vo.StubConfVO;

public interface ICacheCarrier<T extends StubConfVO> extends ITopCache<T> {

    T carryByKey(T searchIns);

}
