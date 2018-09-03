package org.cuber.stub.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.repo.StatusDTO;

@ApiModel("配置父类")
public class StubConfVO extends StubVO{

    private static final long serialVersionUID = -7002707055803854973L;

    @ApiModelProperty("创建者")
    private String createUserId;

    @ApiModelProperty("最近修改者")
    private String updateUserId;

    @ApiModelProperty("状态")
    private String status;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}