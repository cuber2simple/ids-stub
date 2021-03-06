package org.cuber.stub.sso;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.json.JacksonHolder;

import java.io.Serializable;
import java.util.Objects;

@ApiModel("用户组")
public class SSOUserGroup implements Serializable {

    private static final long serialVersionUID = -738941036269966983L;

    @ApiModelProperty("用户组ID")
    private String userGroupId;

    @ApiModelProperty("用户组名")
    private String groupName;

    @ApiModelProperty("用户组描述")
    private String groupDesc;

    @ApiModelProperty("用户组类型")
    private GroupType groupType;

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    @Override
    public int hashCode() {
        return Objects.isNull(userGroupId) ? -1 : userGroupId.hashCode();
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

        SSOUserGroup ssoUserGroup = (SSOUserGroup) obj;
        return StringUtils.equals(ssoUserGroup.getUserGroupId(), userGroupId);
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
