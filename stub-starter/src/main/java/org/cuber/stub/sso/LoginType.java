package org.cuber.stub.sso;

public enum LoginType {
    password("password", "密码登陆"),
    phone("phone", "手机登陆"),
    email("email", "EMAIL登陆"),
    wechat("wechat", "微信登陆"),
    ding("ding", "钉钉登陆"),;
    private String code;
    private String desc;

    LoginType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}