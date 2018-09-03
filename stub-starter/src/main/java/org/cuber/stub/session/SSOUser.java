package org.cuber.stub.session;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public class SSOUser implements UserDetails {

    private static final long serialVersionUID = -3568422755366679290L;

    private String userId;

    private String userName;

    private String nickName;

    private String password;

    private String email;

    private String mobile;

    private String sex;

    private String avatar;

    private String wxOpenId;

    private String dingOpenId;

    private String domain;

    private String loginId;

    private String status;

    private LocalDateTime expireDatetime;

    private Set<SSOUserGroup> ssoUserGroups;

    private Set<SSORole> roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getDingOpenId() {
        return dingOpenId;
    }

    public void setDingOpenId(String dingOpenId) {
        this.dingOpenId = dingOpenId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Set<SSOUserGroup> getSsoUserGroups() {
        return ssoUserGroups;
    }

    public void setSsoUserGroups(Set<SSOUserGroup> ssoUserGroups) {
        this.ssoUserGroups = ssoUserGroups;
    }

    public Set<SSORole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SSORole> roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getExpireDatetime() {
        return expireDatetime;
    }

    public void setExpireDatetime(LocalDateTime expireDatetime) {
        this.expireDatetime = expireDatetime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return LocalDateTime.now().isBefore(expireDatetime);
    }

    @Override
    public boolean isAccountNonLocked() {
        return "1".equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
