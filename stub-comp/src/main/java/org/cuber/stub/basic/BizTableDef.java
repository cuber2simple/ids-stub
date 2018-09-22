package org.cuber.stub.basic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.json.JacksonHolder;
import org.cuber.stub.vo.StubConfVO;

@ApiModel("业务对象")
public class BizTableDef extends StubConfVO {
    private static final long serialVersionUID = -189860677921334237L;

    @ApiModelProperty("表名")
    private String tableName;

    @ApiModelProperty("库名")
    private String schema;

    @ApiModelProperty("所属服务")
    private String appName;

    @ApiModelProperty("分表对象")
    private String tClass;

    @ApiModelProperty("分表模式 yyyy_MM, yyyy_MM_dd")
    private String pattern;

    @ApiModelProperty("业务CODE(0-31)")
    private int bizCode;

    @ApiModelProperty("前缀业务ID")
    private String prefix;

    @ApiModelProperty("业务描述")
    private String theDesc;

    @ApiModelProperty("系统内置")
    private boolean sys;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTClass() {
        return tClass;
    }

    public void setTClass(String tClass) {
        this.tClass = tClass;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getTheDesc() {
        return theDesc;
    }

    public void setTheDesc(String theDesc) {
        this.theDesc = theDesc;
    }

    public boolean isSys() {
        return sys;
    }

    public void setSys(boolean sys) {
        this.sys = sys;
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
