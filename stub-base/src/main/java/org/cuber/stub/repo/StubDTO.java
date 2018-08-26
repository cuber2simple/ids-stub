package org.cuber.stub.repo;

import java.time.LocalDateTime;

public class StubDTO {

    /**
     *  column_name    CREATE_DATETIME
     *  remark         创建时间
     */
    private LocalDateTime createDatetime;


    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }
}
