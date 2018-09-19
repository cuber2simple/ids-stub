package org.cuber.stub.util;

import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.StubConstant;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.rpc.StubException;

import java.util.Objects;

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

    public static <T> Resp<T> httpFailed(int httpStatus, String message, String trace) {
        Resp<T> resp = new Resp<>();
        resp.setAbnormal(true);
        resp.setResultCode(StubConstant.HTTP_FAILED_CODE);
        resp.setHttpStatus(httpStatus);
        resp.setResultMsg(message);
        resp.setTrace(trace);
        return resp;
    }

    public static <T> void verifyBase(Req<T> req) throws StubException {
        StubException stubException = new StubException();
        stubException.setCode(StubConstant.VERIFY_ERR);
        boolean verify = true;
        if (Objects.nonNull(req)) {
            if (!StringUtils.isNotEmpty(req.getAppName())) {
                verify = false;
                stubException.setMessage("请求项目不能为空");
            }

            if (!StringUtils.isNotEmpty(req.getTraceId())) {
                verify = false;
                stubException.setMessage("请求traceId不能为空");
            }
        } else {
            verify = false;
            stubException.setMessage("基本请求对象不能为空");
        }
        if (verify) {
            throw stubException;
        }
    }

    public static <T> void verifyVoNotNull(Req<T> req)throws StubException {
        verifyBase(req);
        StubException stubException = new StubException();
        stubException.setCode(StubConstant.VERIFY_ERR);
        stubException.setMessage("请求对象不能为空");
        if(Objects.isNull(req.getReq())){
            throw stubException;
        }
    }

    public static boolean isSuccess(Resp resp){
        return Objects.nonNull(resp) && resp.isSuccess();
    }
}
