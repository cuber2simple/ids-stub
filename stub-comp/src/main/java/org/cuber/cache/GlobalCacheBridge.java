package org.cuber.cache;

import org.cuber.stub.basic.CacheDef;
import org.cuber.stub.vo.StubConfVO;

public interface GlobalCacheBridge<T extends StubConfVO> {

    T loadByKey(String key);

    CacheDef<T> cacheDef();

}
