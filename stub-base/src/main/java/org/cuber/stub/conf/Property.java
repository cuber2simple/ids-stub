package org.cuber.stub.conf;

import org.cuber.stub.json.GsonHolder;

public class Property {

    private String name;
    private String value;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return GsonHolder.toJson(this);
    }
}
