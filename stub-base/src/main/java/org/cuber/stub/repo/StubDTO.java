package org.cuber.stub.repo;

import org.cuber.stub.json.JacksonHolder;

import java.time.LocalDateTime;

public class StubDTO {

    /**
     *  column_name    CREATE_DATETIME
     *  remark         创建时间
     */
    private LocalDateTime createDatetime;

    /**
     *  column_name    ID
     *  remark         主键
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
