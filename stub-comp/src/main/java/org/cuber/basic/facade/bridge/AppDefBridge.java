package org.cuber.basic.facade.bridge;

import org.cuber.cache.bridge.ICacheBridge;
import org.cuber.stub.basic.AppDef;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.StubException;

public interface AppDefBridge extends ICacheBridge<AppDef> {

    AppDef loadSelf(Req req) throws StubException;
}
