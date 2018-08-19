package org.cuber.gen.service.impl;

import org.cuber.gen.conf.Column;
import org.cuber.gen.conf.Conf;
import org.cuber.gen.conf.Table;
import org.cuber.gen.define.TableDefine;
import org.cuber.gen.service.ConfService;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.Objects;

public class ConfServiceImpl implements ConfService {

    private static JavaTypeResolver javaTypeResolver = new JavaTypeResolverDefaultImpl();

    @Override
    public Table loadTable(Conf conf, TableDefine tableDefine, DatabaseMetaData databaseMetaData) throws Exception {
        String realCatalog = fetchDataBaseName(conf.getCatalog(), databaseMetaData);
        String realSchema = fetchDataBaseName(conf.getSchema(), databaseMetaData);
        String tableName = fetchDataBaseName(tableDefine.getTableName(), databaseMetaData);
        Table table = new Table(tableDefine, conf);
        //
        retrieveTableDesc(realCatalog, realSchema, tableName, table, databaseMetaData);

        return table;
    }

    private void retrieveTableDesc(String realCatalog, String realSchema, String tableName, Table table, DatabaseMetaData databaseMetaData) throws Exception {
        ResultSet rs = databaseMetaData.getTables(realCatalog, realSchema, tableName, null);
        while (rs.next()) {
            String remarks = rs.getString("REMARKS"); //$NON-NLS-1$
            String tableType = rs.getString("TABLE_TYPE"); //$NON-NLS-1$
            table.setRemarks(remarks);
            table.setTableType(tableType);
        }
        JdbcUtils.closeResultSet(rs);
    }


    private void retrieveColumns(String realCatalog, String realSchema, String tableName, Table table, DatabaseMetaData databaseMetaData) throws Exception {
        ResultSet rs = databaseMetaData.getColumns(realCatalog, realSchema,
                tableName, "%");
        boolean supportsIsAutoIncrement = false;
        boolean supportsIsGeneratedColumn = false;
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            if ("IS_AUTOINCREMENT".equals(rsmd.getColumnName(i))) { //$NON-NLS-1$
                supportsIsAutoIncrement = true;
            }
            if ("IS_GENERATEDCOLUMN".equals(rsmd.getColumnName(i))) { //$NON-NLS-1$
                supportsIsGeneratedColumn = true;
            }
        }
    }

    private void addColumn(boolean supportsIsAutoIncrement,boolean supportsIsGeneratedColumn, ResultSet rs, Table table) throws Exception{
        Column introspectedColumn = new Column();
        introspectedColumn.setJdbcType(rs.getInt("DATA_TYPE")); //$NON-NLS-1$
        introspectedColumn.setLength(rs.getInt("COLUMN_SIZE")); //$NON-NLS-1$
        introspectedColumn.setActualColumnName(rs.getString("COLUMN_NAME")); //$NON-NLS-1$
        introspectedColumn
                .setNullable(rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable); //$NON-NLS-1$
        introspectedColumn.setScale(rs.getInt("DECIMAL_DIGITS")); //$NON-NLS-1$
        introspectedColumn.setRemarks(rs.getString("REMARKS")); //$NON-NLS-1$
        introspectedColumn.setDefaultValue(rs.getString("COLUMN_DEF")); //$NON-NLS-1$

        FullyQualifiedJavaType fullyQualifiedJavaType = javaTypeResolver.calculateJavaType(introspectedColumn);
        if (!fullyQualifiedJavaType.isPrimitive() && !fullyQualifiedJavaType.isArray()) {
            table.addColumnType(fullyQualifiedJavaType);
        }
        introspectedColumn
                .setFullyQualifiedJavaType(fullyQualifiedJavaType);
        introspectedColumn.setJdbcTypeName(javaTypeResolver
                .calculateJdbcTypeName(introspectedColumn));
        introspectedColumn.setJavaProperty(JavaBeansUtil.getCamelCaseString(introspectedColumn.getActualColumnName(), false));
        if (supportsIsAutoIncrement) {
            introspectedColumn.setAutoIncrement(
                    "YES".equals(rs.getString("IS_AUTOINCREMENT"))); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (supportsIsGeneratedColumn) {
            introspectedColumn.setGeneratedColumn(
                    "YES".equals(rs.getString("IS_GENERATEDCOLUMN"))); //$NON-NLS-1$ //$NON-NLS-2$
        }
//        if (introspectedColumn.getActualColumnName().equals(tableConfig.getVersionColumn())) {
//            if (Types.NUMERIC == introspectedColumn.getJdbcType() ||
//                    Types.DECIMAL == introspectedColumn.getJdbcType()) {
//                introspectedColumn.setVersion(true);
//                table.setVersion(true);
//                table.setVersionColumn(introspectedColumn);
//            } else {
//                logger.warn("{}.{} 列不是数字类型,将被忽略version属性", tableConfig.getName(), introspectedColumn.getActualColumnName());
//            }
//
//        }
        if (Types.TIMESTAMP == introspectedColumn.getJdbcType()) {
            introspectedColumn.setDateTime(true);
        }
//        if (introspectedColumn.getActualColumnName().equals(tableConfig.getUpdateTimeColumn())) {
//            if (Types.TIMESTAMP == introspectedColumn.getJdbcType()) {
//                introspectedColumn.setUpdateDateTime(true);
//            } else {
//                logger.warn("{}.{} 列不是timeStamp类型,将被忽略updateTime属性", tableConfig.getName(), introspectedColumn.getActualColumnName());
//            }
//        }
//        columns.add(introspectedColumn);
        table.addColumn(introspectedColumn);
    }


    private String fetchDataBaseName(String dest, DatabaseMetaData metaData) throws Exception {
        String target = dest;
        if (metaData.storesLowerCaseIdentifiers()) {
            target = Objects.isNull(target) ? null : target.toLowerCase();
        } else if (metaData.storesUpperCaseIdentifiers()) {
            target = Objects.isNull(target) ? null : target.toUpperCase();
        }
        return target;
    }
}
