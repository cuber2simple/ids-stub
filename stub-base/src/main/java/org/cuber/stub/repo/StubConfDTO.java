package org.cuber.stub.repo;

public class StubConfDTO extends StubDTO{

    /**
     *  column_name    CREATE_OPERATOR
     *  remark         创建用户
     */
    private String createOperator;

    /**
     *  column_name    UPDATE_OPERATOR
     *  remark         更新用户
     */
    private String updateOperator;

    /**
     *  column_name    STATUS
     *  remark         配置状态
     */
    private String status;

    public String getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(String createOperator) {
        this.createOperator = createOperator;
    }

    public String getUpdateOperator() {
        return updateOperator;
    }

    public void setUpdateOperator(String updateOperator) {
        this.updateOperator = updateOperator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
