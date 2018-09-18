package org.cuber.stub.sso;

public class SSOMenu extends SSOResource{
    private static final long serialVersionUID = -4014962413439164653L;

    private String parentId;
    private int treeSort;
    private boolean treeLeaf;
    private int treeLevel;
    private String domain;
    private boolean sys;
    private String status;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getTreeSort() {
        return treeSort;
    }

    public void setTreeSort(int treeSort) {
        this.treeSort = treeSort;
    }

    public boolean isTreeLeaf() {
        return treeLeaf;
    }

    public void setTreeLeaf(boolean treeLeaf) {
        this.treeLeaf = treeLeaf;
    }

    public int getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(int treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isSys() {
        return sys;
    }

    public void setSys(boolean sys) {
        this.sys = sys;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
