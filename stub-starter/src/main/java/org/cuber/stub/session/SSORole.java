package org.cuber.stub.session;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public class SSORole implements GrantedAuthority {

    private static final long serialVersionUID = 4250602050828717462L;

    private String roleId;

    private String roleName;

    private String roleDesc;

    private String roleType;

    private Set<SSOResource> resources;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public void setResources(Set<SSOResource> resources) {
        this.resources = resources;
    }
    @Override
    public String getAuthority() {
        return roleName;
    }
}
