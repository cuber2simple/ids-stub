package org.cuber.stub.rpc;

import org.cuber.stub.StubConstant;

public class RpcUtils {

    public static <T> Resp<T> success(T result){
        Resp<T> resp = new Resp<>();
        resp.setResult(result);
        resp.setResultCode(StubConstant.SUCCESS_CODE);
        return resp;
    }

    public static <T> Resp<T> fail(String resultCode){
        Resp<T> resp = new Resp<>();
        resp.setAbnormal(true);
        resp.setResultCode(resultCode);
        return resp;
    }
}
