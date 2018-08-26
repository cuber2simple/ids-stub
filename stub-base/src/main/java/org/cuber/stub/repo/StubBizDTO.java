package org.cuber.stub.repo;

import java.time.LocalDateTime;

public class StubBizDTO {

    /**
     *  column_name    COMPLETE_DATETIME
     *  remark         完成时间
     */
    private LocalDateTime completeDatetime;

    /**
     *  column_name    BIZ_STATUS
     *  remark         业务状态
     */
    private String bizStatus;

    public LocalDateTime getCompleteDatetime() {
        return completeDatetime;
    }

    public void setCompleteDatetime(LocalDateTime completeDatetime) {
        this.completeDatetime = completeDatetime;
    }

    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }
}
