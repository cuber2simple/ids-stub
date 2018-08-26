package org.cuber.stub.utils;

import org.cuber.stub.StubConstant;
import org.cuber.stub.rpc.Resp;

public class RpcUtils {

    public static <T> Resp<T> success(T result) {
        Resp<T> resp = new Resp<>();
        resp.setResult(result);
        resp.setResultCode(StubConstant.SUCCESS_CODE);
        resp.setSuccess(true);
        return resp;
    }

    public static <T> Resp<T> fail(String resultCode) {
        Resp<T> resp = new Resp<>();
        resp.setSuccess(false);
        resp.setResultCode(resultCode);
        return resp;
    }
}
