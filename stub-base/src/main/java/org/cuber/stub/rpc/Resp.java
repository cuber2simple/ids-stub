package org.cuber.stub.rpc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("rmi基本响应对象")
public class Resp<T> implements Serializable {

    private static final long serialVersionUID = -6548097138557152600L;

    @ApiModelProperty("响应的类型")
    private T result;

    @ApiModelProperty("响应的code")
    private String resultCode;

    @ApiModelProperty("响应的消息")
    private String resultMsg;

    @ApiModelProperty("是否正常返回")
    private boolean abnormal;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public boolean isAbnormal() {
        return abnormal;
    }

    public void setAbnormal(boolean abnormal) {
        this.abnormal = abnormal;
    }
}
