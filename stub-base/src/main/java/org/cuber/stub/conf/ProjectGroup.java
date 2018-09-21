package org.cuber.stub.conf;

import org.cuber.stub.json.GsonHolder;

public class ProjectGroup {
    private String name;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
