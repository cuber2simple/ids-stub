package org.cuber.stub.sso;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public class SSOResource implements Serializable {

    private static final long serialVersionUID = 4214349564120072593L;

    private String resourceId;

    private String resourceName;

    private String resourceDesc;

    private String resourceUrl;

    private String resourceIcon;

    private String resourceZhName;

    private String i18nKey;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }

    public String getResourceZhName() {
        return resourceZhName;
    }

    public void setResourceZhName(String resourceZhName) {
        this.resourceZhName = resourceZhName;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
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

        SSOResource resource = (SSOResource) obj;
        return StringUtils.equals(resource.getResourceId(), resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.isNull(resourceId) ? -1 : resourceId.hashCode();
    }
}
