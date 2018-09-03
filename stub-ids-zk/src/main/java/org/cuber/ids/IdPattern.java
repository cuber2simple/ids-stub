package org.cuber.ids;

import java.io.Serializable;

public class IdPattern<T> implements Serializable {

    private static final long serialVersionUID = 9042671291171356833L;

    private String appName;

    private int bizCode;

    private String prefix;

    private Class<T> dtoClass;

    private String desc;

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Class<T> getDtoClass() {
        return dtoClass;
    }

    public void setDtoClass(Class<T> dtoClass) {
        this.dtoClass = dtoClass;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
