package org.cuber.stub.repo;

public enum StatusDTO {
    ini("0","初始化"),
    eff("1","有效"),
    inv("2","无效"),
    aud("9","审核中")
    ;
    private String code;
    private String desc;

    StatusDTO(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
