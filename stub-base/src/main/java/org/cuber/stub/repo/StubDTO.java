package org.cuber.stub.repo;

import java.time.LocalDateTime;

public class StubDTO {

    /**
     *  column_name    CREATE_DATETIME
     *  remark         创建时间
     */
    private LocalDateTime createDateTime;

    /**
     *  column_name    UPDATE_DATETIME
     *  remark         更新时间
     */
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
