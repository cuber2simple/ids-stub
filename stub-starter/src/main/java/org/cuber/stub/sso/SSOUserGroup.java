package org.cuber.stub.sso;

import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.json.JacksonHolder;

import java.io.Serializable;
import java.util.Objects;

public class SSOUserGroup implements Serializable {

    private static final long serialVersionUID = -738941036269966983L;

    private String userGroupId;

    private String groupName;

    private String groupDesc;

    private String groupType;

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

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
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
