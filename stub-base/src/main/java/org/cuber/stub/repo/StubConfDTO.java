package org.cuber.stub.repo;

import org.cuber.stub.json.JacksonHolder;

public class StubConfDTO extends StubDTO{

    /**
     *  column_name    CREATE_USER_ID
     *  remark         创建用户
     */
    private String createUserId;

    /**
     *  column_name    UPDATE_USER_ID
     *  remark         更新用户
     */
    private String updateUserId;

    /**
     *  column_name    STATUS
     *  remark         配置状态
     */
    private String status;


    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
