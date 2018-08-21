package org.cuber.stub.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.repo.StatusDTO;

@ApiModel("配置父类")
public class StubConfVO extends StubVO{

    private static final long serialVersionUID = -7002707055803854973L;

    @ApiModelProperty("创建者")
    private String createOperator;

    @ApiModelProperty("最近修改者")
    private String updateOperator;

    @ApiModelProperty("状态")
    private StatusDTO status;

    public String getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(String createOperator) {
        this.createOperator = createOperator;
    }

    public String getUpdateOperator() {
        return updateOperator;
    }

    public void setUpdateOperator(String updateOperator) {
        this.updateOperator = updateOperator;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }
}
