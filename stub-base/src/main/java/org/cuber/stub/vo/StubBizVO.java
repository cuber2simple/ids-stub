package org.cuber.stub.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

@ApiModel("业务父类")
public class StubBizVO extends StubVO{

    private static final long serialVersionUID = -1417773604300822625L;

    @ApiModelProperty("完成时间")
    private LocalDateTime completeDateTime;

    public LocalDateTime getCompleteDateTime() {
        return completeDateTime;
    }

    public void setCompleteDateTime(LocalDateTime completeDateTime) {
        this.completeDateTime = completeDateTime;
    }
}
