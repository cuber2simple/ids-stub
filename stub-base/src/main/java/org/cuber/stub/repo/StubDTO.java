package org.cuber.stub.repo;

import java.time.LocalDateTime;

public class StubDTO {

    /**
     *  column_name    CREATE_DATETIME
     *  remark         创建时间
     */
    private LocalDateTime createDateTime;


    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

}
