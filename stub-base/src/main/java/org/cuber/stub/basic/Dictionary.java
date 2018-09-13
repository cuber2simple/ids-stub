package org.cuber.stub.basic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.json.GsonHolder;
import org.cuber.stub.vo.StubConfVO;

import java.util.List;

@ApiModel("数据字典")
public class Dictionary extends StubConfVO {

    private static final long serialVersionUID = 7161010203775977559L;

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("描述")
    private String theDesc;

    @ApiModelProperty("字典子项")
    private List<DictionaryData> dictionaryItem;

    @ApiModelProperty("是否系统使用")
    private boolean sys;

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

    public List<DictionaryData> getDictionaryItem() {
        return dictionaryItem;
    }

    public void setDictionaryItem(List<DictionaryData> dictionaryItem) {
        this.dictionaryItem = dictionaryItem;
    }

    public boolean isSys() {
        return sys;
    }

    public void setSys(boolean sys) {
        this.sys = sys;
    }

    @Override
    public String toString() {
        return GsonHolder.toJson(this);
    }

    public static class DictionaryData extends StubConfVO {

        private static final long serialVersionUID = -3231691927352870712L;

        @ApiModelProperty("字典编码")
        private String dictCode;

        @ApiModelProperty("父节点编码")
        private String parentCode;

        @ApiModelProperty("树排序")
        private int treeSort;

        @ApiModelProperty("叶子节点")
        private boolean treeLeaf;

        @ApiModelProperty("叶子深度")
        private int treeLevel;

        @ApiModelProperty("字典标志")
        private String dictLabel;

        @ApiModelProperty("字典值")
        private String dictValue;

        @ApiModelProperty("字典")
        private String dictName;

        @ApiModelProperty("字典标识")
        private String dictTag;

        @ApiModelProperty("系统使用")
        private boolean sys;

        @ApiModelProperty("默认选中")
        private boolean defaultX;

        @ApiModelProperty("描述")
        private String theDesc;

        @ApiModelProperty("字典子项")
        private List<DictionaryData> subItems;

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

        public String getDictName() {
            return dictName;
        }

        public void setDictName(String dictName) {
            this.dictName = dictName;
        }

        public String getDictTag() {
            return dictTag;
        }

        public void setDictTag(String dictTag) {
            this.dictTag = dictTag;
        }

        public boolean isSys() {
            return sys;
        }

        public void setSys(boolean sys) {
            this.sys = sys;
        }

        public boolean isDefaultX() {
            return defaultX;
        }

        public void setDefaultX(boolean defaultX) {
            this.defaultX = defaultX;
        }

        public String getTheDesc() {
            return theDesc;
        }

        public void setTheDesc(String theDesc) {
            this.theDesc = theDesc;
        }

        public List<DictionaryData> getSubItems() {
            return subItems;
        }

        public void setSubItems(List<DictionaryData> subItems) {
            this.subItems = subItems;
        }

        @Override
        public String toString() {
            return GsonHolder.toJson(this);
        }
    }
}
