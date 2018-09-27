package org.cuber.stub.sso;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.json.JacksonHolder;

import java.io.Serializable;

@ApiModel("登陆风格")
public class LoginStyle implements Serializable {

    private static final long serialVersionUID = -408112222809130127L;

    @ApiModelProperty("身份认证")
    private String identifier;

    @ApiModelProperty("密码或者code")
    private String pin;

    @ApiModelProperty("域名")
    private String domain;

    @ApiModelProperty("登陆方式")
    private LoginType loginType;


    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
