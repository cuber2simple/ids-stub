package org.cuber.stub.vo;

public class SpringBean {
    private Object bean;
    private String beanName;

    public SpringBean() {
    }

    public SpringBean(Object bean, String beanName) {
        this.bean = bean;
        this.beanName = beanName;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
