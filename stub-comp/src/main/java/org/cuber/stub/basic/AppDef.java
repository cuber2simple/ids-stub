package org.cuber.stub.basic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.json.GsonHolder;
import org.cuber.stub.vo.StubConfVO;

@ApiModel("服务对象")
public class AppDef extends StubConfVO {

    private static final long serialVersionUID = -1950841639244717937L;

    @ApiModelProperty("服务名称")
    private String appName;

    @ApiModelProperty("服务所属组名")
    private String groupName;

    @ApiModelProperty("服务管理的用户ID")
    private String ownUserId;

    @ApiModelProperty("服务端口号")
    private int serverPort;

    @ApiModelProperty("服务dubbo端口号")
    private int dubboPort;

    @ApiModelProperty("kafka话题,通常用于任务下发")
    private String kafkaTopic;

    @ApiModelProperty("额外设定参数配置")
    private String extSetting;

    @ApiModelProperty("额外设定参数配置1")
    private String ext1Setting;

    @ApiModelProperty("设定的中心path")
    private String contextPath;

    @ApiModelProperty("服务描述")
    private String appDesc;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOwnUserId() {
        return ownUserId;
    }

    public void setOwnUserId(String ownUserId) {
        this.ownUserId = ownUserId;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getDubboPort() {
        return dubboPort;
    }

    public void setDubboPort(int dubboPort) {
        this.dubboPort = dubboPort;
    }

    public String getExtSetting() {
        return extSetting;
    }

    public void setExtSetting(String extSetting) {
        this.extSetting = extSetting;
    }

    public String getExt1Setting() {
        return ext1Setting;
    }

    public void setExt1Setting(String ext1Setting) {
        this.ext1Setting = ext1Setting;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    @Override
    public String toString() {
        return GsonHolder.toJson(this);
    }
}
