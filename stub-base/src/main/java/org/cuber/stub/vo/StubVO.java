package org.cuber.stub.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("基本的数据祖类")
public class StubVO implements Serializable {

    private static final long serialVersionUID = -5279892905408745157L;

    @ApiModelProperty("创建时间")
    private LocalDateTime createDateTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateDateTime;

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
