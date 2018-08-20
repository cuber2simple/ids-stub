package org.cuber.gen.conf;

import com.google.common.base.Joiner;
import org.apache.commons.collections4.CollectionUtils;
import org.cuber.gen.define.TableDefine;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Table extends IntrospectedTableMyBatis3Impl {

    private Set<FullyQualifiedJavaType> javaTypeList = new HashSet<>();
    private FullyQualifiedJavaType primaryJavType;
    private FullyQualifiedJavaType dtoJavaType;
    private FullyQualifiedJavaType voJavaType;
    private FullyQualifiedJavaType mapperJavaType;
    private FullyQualifiedJavaType serviceJavaType;
    private FullyQualifiedJavaType serviceImplJavaType;
    private FullyQualifiedJavaType controllerJavaType;
    private TableDefine tableDefine;
    private boolean version;
    private Column versionColumn;

    private List<Column> allColumns;

    private boolean hasPrimary;


    public boolean isHasPrimary() {
        return hasPrimary;
    }

    public void setHasPrimary(boolean hasPrimary) {
        this.hasPrimary = hasPrimary;
    }

    public Column getVersionColumn() {
        return versionColumn;
    }

    public void setVersionColumn(Column versionColumn) {
        this.versionColumn = versionColumn;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public void setAllColumnType(Set<FullyQualifiedJavaType> allColumnType) {
        this.allColumnType = allColumnType;
    }

    private Set<FullyQualifiedJavaType> allColumnType = new HashSet<>();


    public Table(TableDefine tableDefine, Conf conf) {
        this.tableDefine = tableDefine;
        this.dtoJavaType = new FullyQualifiedJavaType(conf.getDto().getDestPackage() + "." + tableDefine.getDtoName());
        String dtoName = dtoJavaType.getShortName();
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

    public FullyQualifiedJavaType getDtoJavaType() {
        return dtoJavaType;
    }

    public void setDtoJavaType(FullyQualifiedJavaType dtoJavaType) {
        this.dtoJavaType = dtoJavaType;
    }

    public void setAllColumns(List<Column> allColumns) {
        this.allColumns = allColumns;
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

    public void addColumnType(FullyQualifiedJavaType columnType) {
        allColumnType.add(columnType);
    }


    public boolean isProperties(String name) {
        List<IntrospectedColumn> allColumn = this.getAllColumns();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        allColumn.forEach(introspectedColumn -> {
            if (introspectedColumn.getJavaProperty().equals(name)) {
                atomicBoolean.getAndSet(true);
            }
        });
        return atomicBoolean.get();
    }

    public List<Column> allColumns() {
        if (allColumns == null) {
            List<IntrospectedColumn> introspectedColumns = this.getAllColumns();
            if (CollectionUtils.isNotEmpty(introspectedColumns)) {
                allColumns = introspectedColumns.stream()
                        .map(introspectedColumn -> (Column) introspectedColumn)
                        .collect(Collectors.toList());
            }
        }
        return allColumns;
    }

    public List<Column> showColumns(String rootClass) {
        List<Column> showColumns = allColumns();
        try {
            if (CollectionUtils.isNotEmpty(showColumns)) {
                List<PropertyDescriptor> propertyDescriptors = Arrays.asList(BeanUtils.getPropertyDescriptors(Class.forName(rootClass)));
                showColumns = showColumns.stream()
                        .filter(column -> !inProperties(column.getJavaProperty(), propertyDescriptors))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showColumns;
    }

    private boolean inProperties(String name, List<PropertyDescriptor> propertyDescriptors) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        if (CollectionUtils.isNotEmpty(propertyDescriptors)) {
            propertyDescriptors.stream().forEach(propertyDescriptor -> {
                if (propertyDescriptor.getName().equals(name)) {
                    atomicBoolean.getAndSet(true);
                }
            });
        }
        return atomicBoolean.get();
    }

    //todo 以后增加方言
    private String getColumnValue(Column column, String dialect) {
        if (column.isDateTime()) {
            return "current_timestamp";
        } else {
            return "#{" + column.getJavaProperty() + ", jdbcType = " + column.getJdbcTypeName() + "}";
        }
    }

    public String base_Column_List() {
        return Joiner.on(", ").join(this.getAllColumns().stream().map(introspectedColumn ->
                introspectedColumn.getActualColumnName().toUpperCase())
                .collect(Collectors.toList()));
    }


    public String base_insert_value() {
        return Joiner.on(", ").join(this.getAllColumns().stream().map(introspectedColumn ->
                getColumnValue((Column) introspectedColumn, null))
                .collect(Collectors.toList()));
    }

    public String whereCondition(List<Column> columnList) {
        if (CollectionUtils.isNotEmpty(columnList)) {
            return Joiner.on("AND ").join(columnList.stream().map(introspectedColumn ->
                    introspectedColumn.getActualColumnName() + " = " + getColumnValue(introspectedColumn, null))
                    .collect(Collectors.toList()));
        } else {
            return "";
        }

    }
}
