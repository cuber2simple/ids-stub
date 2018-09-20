package org.cuber.stub.interceptor;

import org.cuber.anno.TableSplitStrategy;

public class MybatisTableSplitStrategy {

    private String sqlId;
    private boolean split;
    private TableSplitStrategy tableSplitStrategy;

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public TableSplitStrategy getTableSplitStrategy() {
        return tableSplitStrategy;
    }

    public void setTableSplitStrategy(TableSplitStrategy tableSplitStrategy) {
        this.tableSplitStrategy = tableSplitStrategy;
    }
}
