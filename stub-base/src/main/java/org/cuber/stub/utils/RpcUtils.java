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

    public static <T> Resp<T> httpFailed(int httpStatus, String message,String trace) {
        Resp<T> resp = new Resp<>();
        resp.setAbnormal(true);
        resp.setResultCode(StubConstant.HTTP_FAILED_CODE);
        resp.setHttpStatus(httpStatus);
        resp.setResultMsg(message);
        resp.setTrace(trace);
        return resp;
    }

}
