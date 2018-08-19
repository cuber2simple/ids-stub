package org.cuber.gen.conf;

import org.cuber.gen.define.TableDefine;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Table extends IntrospectedTableMyBatis3Impl {

    private Set<FullyQualifiedJavaType> javaTypeList = new HashSet<>();
    private FullyQualifiedJavaType primaryJavType;
    private FullyQualifiedJavaType entityJavaType;
    private FullyQualifiedJavaType voJavaType;
    private FullyQualifiedJavaType mapperJavaType;
    private FullyQualifiedJavaType serviceJavaType;
    private FullyQualifiedJavaType serviceImplJavaType;
    private FullyQualifiedJavaType controllerJavaType;
    private TableDefine tableDefine;

    private Set<FullyQualifiedJavaType> allColumnType = new HashSet<>();


    public Table(TableDefine tableDefine,Conf conf) {
        this.tableDefine = tableDefine;
        this.entityJavaType = new FullyQualifiedJavaType(conf.getDto().getDestPackage() + "." + tableDefine.getDtoName());
        String dtoName = entityJavaType.getShortName();
        this.voJavaType = new FullyQualifiedJavaType(conf.getVo().getDestPackage() + "." + dtoName + "VO");
        this.mapperJavaType = new FullyQualifiedJavaType(conf.getMapper().getDestPackage() + "." + dtoName + "Mapper");
        this.serviceJavaType = new FullyQualifiedJavaType(conf.getService().getDestPackage() + "." + dtoName + "Service");
        this.serviceImplJavaType = new FullyQualifiedJavaType(conf.getService().getDestPackage() + ".impl." + dtoName + "ServiceImpl");
        this.controllerJavaType = new FullyQualifiedJavaType(conf.getCtrl().getDestPackage() + "." + dtoName + "Controller");
    }

    public TableDefine getTableDefine() {
        return tableDefine;
    }

    public void setTableDefine(TableDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public FullyQualifiedJavaType getServiceImplJavaType() {
        return serviceImplJavaType;
    }

    public void setServiceImplJavaType(FullyQualifiedJavaType serviceImplJavaType) {
        this.serviceImplJavaType = serviceImplJavaType;
    }

    public Set<FullyQualifiedJavaType> getJavaTypeList() {
        return javaTypeList;
    }

    public void setJavaTypeList(Set<FullyQualifiedJavaType> javaTypeList) {
        this.javaTypeList = javaTypeList;
    }

    public FullyQualifiedJavaType getPrimaryJavType() {
        return primaryJavType;
    }

    public void setPrimaryJavType(FullyQualifiedJavaType primaryJavType) {
        this.primaryJavType = primaryJavType;
    }

    public FullyQualifiedJavaType getEntityJavaType() {
        return entityJavaType;
    }

    public void setEntityJavaType(FullyQualifiedJavaType entityJavaType) {
        this.entityJavaType = entityJavaType;
    }

    public FullyQualifiedJavaType getVoJavaType() {
        return voJavaType;
    }

    public void setVoJavaType(FullyQualifiedJavaType voJavaType) {
        this.voJavaType = voJavaType;
    }

    public FullyQualifiedJavaType getMapperJavaType() {
        return mapperJavaType;
    }

    public void setMapperJavaType(FullyQualifiedJavaType mapperJavaType) {
        this.mapperJavaType = mapperJavaType;
    }

    public FullyQualifiedJavaType getServiceJavaType() {
        return serviceJavaType;
    }

    public void setServiceJavaType(FullyQualifiedJavaType serviceJavaType) {
        this.serviceJavaType = serviceJavaType;
    }

    public FullyQualifiedJavaType getControllerJavaType() {
        return controllerJavaType;
    }

    public void setControllerJavaType(FullyQualifiedJavaType controllerJavaType) {
        this.controllerJavaType = controllerJavaType;
    }

    public Set<FullyQualifiedJavaType> getAllColumnType() {
        return allColumnType;
    }

    public void addColumnType(FullyQualifiedJavaType columnType){
        allColumnType.add(columnType);
    }
}
