package org.cuber.stub.sso.session;

import org.springframework.context.annotation.Description;

import java.io.Serializable;

@Description("bad [http redis session only support Serializable] so far")
public class XUser implements Serializable {

    private static final long serialVersionUID = -1636729935570343294L;
    private String userName;
    private String password;
    private String loginId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
