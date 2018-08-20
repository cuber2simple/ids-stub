package org.cuber.gen.conf;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.internal.util.JavaBeansUtil;

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

    public String getMethod(){
        if(this != null){
            StringBuilder sb = new StringBuilder();
            sb.append("public ");
            sb.append(this.getFullyQualifiedJavaType().getShortName());
            sb.append(" ").append(JavaBeansUtil.getGetterMethodName(this.javaProperty,this.fullyQualifiedJavaType));
            sb.append("(){\n");
            sb.append("        return ").append(this.javaProperty).append(";\n");
            sb.append("    }\n");
            return sb.toString();
        }else{
            return null;
        }
    }

    public String setMethod(){
        if(this != null){
            StringBuilder sb = new StringBuilder();
            sb.append("public void ");
            sb.append(JavaBeansUtil.getSetterMethodName(this.javaProperty));
            sb.append("(").append(this.getFullyQualifiedJavaType().getShortName()).append(" ").append(this.javaProperty).append("){\n");
            if(this.isStringColumn()){
                sb.append("       this."); //$NON-NLS-1$
                sb.append(javaProperty);
                sb.append(" = "); //$NON-NLS-1$
                sb.append(javaProperty);
                sb.append(" == null ? null : "); //$NON-NLS-1$
                sb.append(javaProperty);
                sb.append(".trim();"); //$NON-NLS-1$
            }else{
                sb.append("        this."); //$NON-NLS-1$
                sb.append(javaProperty);
                sb.append(" = "); //$NON-NLS-1$
                sb.append(javaProperty);
                sb.append(';');
            }
            sb.append("\n");
            sb.append("    }\n");
            return sb.toString();
        }else{
            return null;
        }
    }
}
