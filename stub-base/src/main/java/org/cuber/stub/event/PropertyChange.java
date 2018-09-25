package org.cuber.stub.event;

import org.cuber.stub.conf.Property;
import org.springframework.context.ApplicationEvent;

public class PropertyChange extends ApplicationEvent {

    public PropertyChange(Object source) {
        super(source);
    }

    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
