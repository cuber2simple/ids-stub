package org.cuber.stub.foundation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.vo.StubVO;

import java.util.List;


@ApiModel("字典表")
public class DDictionary extends StubVO {
    private static final long serialVersionUID = 8391826420588656296L;
    @ApiModelProperty("字典名称")
    private String dictName;
    @ApiModelProperty("字典类型")
    private String dictType;
    @ApiModelProperty("描述")
    private String theDesc;
    @ApiModelProperty("是否系统使用 Y/N")
    private String isSys;
    @ApiModelProperty("是否可用")
    private String status;
    @ApiModelProperty("更新操作员")
    private String updateUserId;
    @ApiModelProperty("创建操作员")
    private String createUserId;

    private List<DictionaryData> data;

    public List<DictionaryData> getData() {
        return data;
    }

    public void setData(List<DictionaryData> data) {
        this.data = data;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getTheDesc() {
        return theDesc;
    }

    public void setTheDesc(String theDesc) {
        this.theDesc = theDesc;
    }

    public String getIsSys() {
        return isSys;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    @ApiModel("字典数据表")
    public static class DictionaryData  extends StubVO {

        private static final long serialVersionUID = 7669657093971292621L;

        @ApiModelProperty("字典编码")
        private String dictCode;
        @ApiModelProperty("父节点编码")
        private String parentCode;
        @ApiModelProperty("树排序")
        private Short treeSort;
        @ApiModelProperty("是否叶子节点  Y/N")
        private String treeLeaf;
        @ApiModelProperty("叶子深度")
        private Short treeLevel;
        @ApiModelProperty("字典标志")
        private String dictLabel;
        @ApiModelProperty("字典值")
        private String dictValue;
        @ApiModelProperty("字典类型")
        private String dictType;
        @ApiModelProperty("字典标识")
        private String dictTag;
        @ApiModelProperty("是否系统使用 Y/N")
        private String isSys;
        @ApiModelProperty("是否默认选中 Y/N")
        private String isDefault;
        @ApiModelProperty("描述")
        private String theDesc;
        @ApiModelProperty("是否可用")
        private String status;
        @ApiModelProperty("更新操作员")
        private String updateUserId;
        @ApiModelProperty("创建操作员")
        private String createUserId;

        private List<DictionaryData> subData;

        public List<DictionaryData> getSubData() {
            return subData;
        }

        public void setSubData(List<DictionaryData> subData) {
            this.subData = subData;
        }

        public static long getSerialVersionUID() {
            return serialVersionUID;
        }

        public String getDictCode() {
            return dictCode;
        }

        public void setDictCode(String dictCode) {
            this.dictCode = dictCode;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
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

        public String getDictLabel() {
            return dictLabel;
        }

        public void setDictLabel(String dictLabel) {
            this.dictLabel = dictLabel;
        }

        public String getDictValue() {
            return dictValue;
        }

        public void setDictValue(String dictValue) {
            this.dictValue = dictValue;
        }

        public String getDictType() {
            return dictType;
        }

        public void setDictType(String dictType) {
            this.dictType = dictType;
        }

        public String getDictTag() {
            return dictTag;
        }

        public void setDictTag(String dictTag) {
            this.dictTag = dictTag;
        }

        public String getIsSys() {
            return isSys;
        }

        public void setIsSys(String isSys) {
            this.isSys = isSys;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public String getTheDesc() {
            return theDesc;
        }

        public void setTheDesc(String theDesc) {
            this.theDesc = theDesc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(String updateUserId) {
            this.updateUserId = updateUserId;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }
    }
}
