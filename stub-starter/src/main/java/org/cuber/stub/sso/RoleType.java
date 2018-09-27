package org.cuber.stub.sso;

public enum RoleType {
    permission("权限角色"),
    workflow("流程角色");

    RoleType(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
