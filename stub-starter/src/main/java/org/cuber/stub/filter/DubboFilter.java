package org.cuber.stub.filter;

import com.alibaba.dubbo.rpc.*;
import org.apache.logging.log4j.ThreadContext;
import org.cuber.stub.StubConstant;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.rpc.StubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class DubboFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(DubboFilter.class);
    private static ConcurrentHashMap<String, Class> filterRespClass = new ConcurrentHashMap<>();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        String srvPath = invoker.getInterface() + "." + invocation.getMethodName();
        Object[] objects = invocation.getArguments();
        if (objects != null && objects.length == 1 && (objects[0] instanceof Req)) {
            Req req = (Req) objects[0];
            ThreadContext.put(StubConstant.TRACE_ID, req.getTraceId());
        }
        long start = System.currentTimeMillis();//开始秒数
        logger.info("dubbo调用开始:[{}],参数为:[{}]", srvPath, Arrays.toString(invocation.getArguments()));
        try {
            result = invoker.invoke(invocation);
        } catch (Exception e) {
            logger.error("dubbo调用失败:[" + srvPath + "]", e);
            if (filterRespClass.contains(srvPath) && figureOutClass(invoker, invocation, srvPath)) {
                try {
                    if (Resp.class.isAssignableFrom(filterRespClass.get(srvPath))) {
                        Resp resp = (Resp) filterRespClass.get(srvPath).newInstance();
                        resp.setAbnormal(true);
                        if (e instanceof StubException) {
                            StubException stubException = (StubException) e;
                            resp.setResultCode(stubException.getCode());
                        } else {
                            resp.setResultCode(StubConstant.UNKNOWN_ERR);
                        }
                        resp.setResultMsg(e.getMessage());
                        result = new RpcResult(resp);
                    }
                } catch (Exception e1) {
                    logger.error("没有提供空的构造方法", e1);
                }

            }
        } finally {
            logger.info("dubbo调用花费:[{}]毫秒,[{}]结果为:[{}]", System.currentTimeMillis() - start, srvPath, result);
        }
        return result;
    }

    private boolean figureOutClass(Invoker<?> invoker, Invocation invocation, String srvPath) {
        boolean findMethod = false;
        try {
            Method[] methods = Class.forName(invoker.getInterface().toString()).getMethods();
            int i = 0;
            Method method = null;
            if (methods != null && methods.length > 0) {
                for (Method method1 : methods) {
                    if (method.getName().equals(invocation.getMethodName())) {
                        i++;
                        method = method1;
                        break;
                    }
                }
            }
            findMethod = i == 1;
            if (findMethod) {
                filterRespClass.put(srvPath, method.getReturnType());
            }
        } catch (Exception e) {
            logger.error("查找方法报错:", e);
        }
        return findMethod;
    }
}
