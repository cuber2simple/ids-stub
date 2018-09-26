package org.cuber.stub.sso;

import org.cuber.stub.json.JacksonHolder;

import java.io.Serializable;

public class LoginStyle implements Serializable {

    private static final long serialVersionUID = -408112222809130127L;

    private String identifier;

    private String pin;

    private String domain;

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
