package org.cuber.stub.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.cuber.stub.StubConstant;
import org.cuber.stub.conf.Property;
import org.cuber.stub.rpc.Resp;

import java.util.Map;

public class RpcUtils {

    public static <T> Resp<T> success(T result){
        Resp<T> resp = new Resp<>();
        resp.setResult(result);
        resp.setResultCode(StubConstant.SUCCESS_CODE);
        resp.setSuccess(true);
        return resp;
    }

    public static <T> Resp<T> fail(String resultCode){
        Resp<T> resp = new Resp<>();
        resp.setSuccess(false);
        resp.setResultCode(resultCode);
        return resp;
    }

    public static void main(String[] args) throws Exception{
        Property property = new Property();
        property.setName("com.lang");
        Map map = PropertyUtils.describe(property);
        System.out.println(map);
    }
}
