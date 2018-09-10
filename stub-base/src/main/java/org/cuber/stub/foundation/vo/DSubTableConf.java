package org.cuber.stub.foundation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.vo.StubVO;

@ApiModel("分表配置表")
public class DSubTableConf extends StubVO {
    private static final long serialVersionUID = -1624719874726364375L;

    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("库名")
    private String schema;
    @ApiModelProperty("服务名")
    private String appName;
    @ApiModelProperty("类全名")
    private String tClass;
    @ApiModelProperty("分表模式 yyyy_MM, yyyy_MM_dd")
    private String pattern;
    @ApiModelProperty("业务CODE(0-31)")
    private Short bizCode;
    @ApiModelProperty("ID前缀")
    private String prefix;
    @ApiModelProperty("业务分表描述")
    private String theDesc;
    @ApiModelProperty("是否系统")
    private String isSys;
    @ApiModelProperty("是否可用")
    private String status;
    @ApiModelProperty("更新操作员")
    private String updateUserId;
    @ApiModelProperty("创建操作员")
    private String createUserId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String gettClass() {
        return tClass;
    }

    public void settClass(String tClass) {
        this.tClass = tClass;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Short getBizCode() {
        return bizCode;
    }

    public void setBizCode(Short bizCode) {
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
}
