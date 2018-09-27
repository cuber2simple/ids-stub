package org.cuber.stub.sso;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.json.JacksonHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.Set;

@ApiModel("权限")
public class SSORole implements GrantedAuthority {

    private static final long serialVersionUID = 4250602050828717462L;

    @ApiModelProperty("权限ID")
    private String roleId;

    @ApiModelProperty("权限名称")
    private String roleName;

    @ApiModelProperty("权限描述")
    private String roleDesc;

    @ApiModelProperty("权限类型")
    private RoleType roleType;

    @ApiModelProperty("权限拥有的资源")
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

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public void setResources(Set<SSOResource> resources) {
        this.resources = resources;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    public Set<SSOResource> getResources() {
        return resources;
    }

    @Override
    public int hashCode() {
        return Objects.isNull(roleId) ? -1 : roleId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // 如果传入的对象为空,则返回false
        if (obj == null) {
            return false;
        }

        // 如果两者属于不同的类型,不能相等
        if (getClass() != obj.getClass()) {
            return false;
        }

        SSORole role = (SSORole) obj;
        return StringUtils.equals(role.getRoleId(), roleId);
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
