package org.cuber.stub.session;

import java.io.Serializable;

public class SSOResource implements Serializable {

    private static final long serialVersionUID = 4214349564120072593L;

    private String permissionId;

    private String menuId;

    private String permissionName;

    private String permissionDesc;

    private String menuName;

    private String menuDesc;

    private String menuType;

    private String menuParentId;

    private String menuUrl;

    private String menuIcon;

    private String menuDisplayEn;

    private String menuDisplayZh;

    private String i18nKey;

    private Short treeSort;

    private String treeLeaf;

    private Short treeLevel;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuDisplayEn() {
        return menuDisplayEn;
    }

    public void setMenuDisplayEn(String menuDisplayEn) {
        this.menuDisplayEn = menuDisplayEn;
    }

    public String getMenuDisplayZh() {
        return menuDisplayZh;
    }

    public void setMenuDisplayZh(String menuDisplayZh) {
        this.menuDisplayZh = menuDisplayZh;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public Short getTreeSort() {
        return treeSort;
    }

    public void setTreeSort(Short treeSort) {
        this.treeSort = treeSort;
    }

    public String getTreeLeaf() {
        return treeLeaf;
    }

    public void setTreeLeaf(String treeLeaf) {
        this.treeLeaf = treeLeaf;
    }

    public Short getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Short treeLevel) {
        this.treeLevel = treeLevel;
    }
}
