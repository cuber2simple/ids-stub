package org.cuber.gen.conf;

import org.mybatis.generator.api.IntrospectedColumn;

public class Column extends IntrospectedColumn {

    private boolean version;
    private boolean dateTime;

    public boolean isDateTime() {
        return dateTime;
    }

    public void setDateTime(boolean dateTime) {
        this.dateTime = dateTime;
    }
    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

}
