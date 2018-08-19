package org.cuber.gen.enums;

public enum  GenDefineEnum {
    dto("dto","/dto.btl"),
    mapper("mapper","/mapper.btl"),
    xml("xml","/xml.btl"),
    vo("vo","/vo.btl"),
    service("service","/service.btl"),
    ctrl("ctrl","/ctrl.btl"),
    view("view","/view.btl"),
    ;
    GenDefineEnum(String code, String template) {
        this.code = code;
        this.template = template;
    }

    private String code;
    private String template;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
