package org.cuber.stub.sso;

public enum GroupType {
    role("角色组"),
    report("报警组");
    private String desc;

    GroupType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
