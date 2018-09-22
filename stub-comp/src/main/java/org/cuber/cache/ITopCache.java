package org.cuber.cache;

import org.cuber.stub.basic.CacheDef;
import org.cuber.stub.vo.StubConfVO;

public interface ITopCache<T extends StubConfVO> {
    CacheDef loadDef();
}
