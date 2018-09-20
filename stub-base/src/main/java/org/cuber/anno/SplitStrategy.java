package org.cuber.anno;

public enum  SplitStrategy {
    equals("相等"),
    between("在什么之间"),
    untilNow("从某个时间到现在"),
    current("当前");
    private String desc;

    SplitStrategy(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
