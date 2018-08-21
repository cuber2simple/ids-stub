package org.cuber.gen.define;

public class GenDefine {

    private String template;

    private String destProject;

    private String destPackage;

    private String rootClass;

    private String templateImpl;


    public String getTemplateImpl() {
        return templateImpl;
    }

    public void setTemplateImpl(String templateImpl) {
        this.templateImpl = templateImpl;
    }

    public String getRootClass() {
        return rootClass;
    }

    public void setRootClass(String rootClass) {
        this.rootClass = rootClass;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getDestProject() {
        return destProject;
    }

    public void setDestProject(String destProject) {
        this.destProject = destProject;
    }

    public String getDestPackage() {
        return destPackage;
    }

    public void setDestPackage(String destPackage) {
        this.destPackage = destPackage;
    }
}
