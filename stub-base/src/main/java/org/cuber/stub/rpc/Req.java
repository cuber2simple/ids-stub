package org.cuber.stub.rpc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.cuber.stub.StubConstant;
import org.cuber.stub.json.JacksonHolder;

import java.io.Serializable;
import java.util.UUID;

@ApiModel("rmi基本请求对象")
public class Req<T> implements Serializable {

    private static final long serialVersionUID = -7713976139243315747L;

    @ApiModelProperty("请求的traceId")
    private String traceId;

    @ApiModelProperty("请求的项目名")
    private String appName;

    @ApiModelProperty("中心名")
    private String center;

    @ApiModelProperty("基本请求对象")
    private T req;

    public Req() {
        center = System.getProperty(StubConstant.CENTER,StubConstant.CENTER);
        appName = System.getProperty(StubConstant.APP_NAME,StubConstant.DEFAULT_APP);
        traceId =  ThreadContext.get(StubConstant.TRACE_ID);
        if(StringUtils.isEmpty(traceId)){
            traceId = UUID.randomUUID().toString().replace("-", "");
        }
    }

    public Req(T req) {
        this();
        this.req = req;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public T getReq() {
        return req;
    }

    public void setReq(T req) {
        this.req = req;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
