package org.cuber.stub.repo;

import java.time.LocalDateTime;

public class StubBizDTO {

    /**
     *  column_name    COMPLETE_DATETIME
     *  remark         完成时间
     */
    private LocalDateTime completeDateTime;

    /**
     *  column_name    BIZ_STATUS
     *  remark         业务状态
     */
    private String bizStatus;

    public LocalDateTime getCompleteDateTime() {
        return completeDateTime;
    }

    public void setCompleteDateTime(LocalDateTime completeDateTime) {
        this.completeDateTime = completeDateTime;
    }

    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }
}
