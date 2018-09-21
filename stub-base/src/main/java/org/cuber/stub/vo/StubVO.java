package org.cuber.stub.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.json.JacksonHolder;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("基本的数据祖类")
public class StubVO implements Serializable {

    private static final long serialVersionUID = -5279892905408745157L;

    @ApiModelProperty("创建时间")
    private LocalDateTime createDatetime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateDatetime;

    @ApiModelProperty("主键")
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }

    public LocalDateTime getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(LocalDateTime updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
