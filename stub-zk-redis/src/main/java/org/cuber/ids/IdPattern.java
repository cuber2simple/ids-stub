package org.cuber.ids;

import java.io.Serializable;

public class IdPattern implements Serializable {

    private static final long serialVersionUID = 9042671291171356833L;

    private String namespace;

    private String center;

    private String bizCode;

    private String timePattern;

    private int seqLength;

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    public int getSeqLength() {
        return seqLength;
    }

    public void setSeqLength(int seqLength) {
        this.seqLength = seqLength;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IdPattern other = (IdPattern) obj;
        return this.getSignal().equals(other.getSignal());
    }

    private String getSignal(){
        StringBuilder sb = new StringBuilder();
        sb.append(namespace).append("-").append(center).append("-")
                .append(bizCode).append("-").append(timePattern).append("-")
                .append(seqLength);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return getSignal().hashCode();
    }
}
