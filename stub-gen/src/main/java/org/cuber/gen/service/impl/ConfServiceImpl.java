package org.cuber.gen.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.cuber.gen.conf.Column;
import org.cuber.gen.conf.Conf;
import org.cuber.gen.conf.Table;
import org.cuber.gen.define.TableDefine;
import org.cuber.gen.service.ConfService;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class ConfServiceImpl implements ConfService {

    private static JavaTypeResolver javaTypeResolver = new JavaTypeResolverDefaultImpl();

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Table> dispose(Conf conf) {
        try {
            Properties properties = new Properties();
            properties
                    .setProperty(PropertyRegistry.TYPE_RESOLVER_USE_JSR310_TYPES, "true");
            javaTypeResolver.addConfigurationProperties(properties);
            DatabaseMetaData databaseMetaData = dataSource.getConnection().getMetaData();
            List<TableDefine> tableDefines = conf.getTables();
            if (CollectionUtils.isNotEmpty(tableDefines)) {
                return tableDefines.parallelStream().map(tableDefine ->
                        loadTable(conf, tableDefine, databaseMetaData)
                ).collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Table loadTable(Conf conf, TableDefine tableDefine, DatabaseMetaData databaseMetaData) {
        Table table = new Table(tableDefine, conf);
        try {
            String realCatalog = fetchDataBaseName(conf.getCatalog(), databaseMetaData);

            String realSchema = fetchDataBaseName(conf.getSchema(), databaseMetaData);

            String tableName = fetchDataBaseName(tableDefine.getTableName(), databaseMetaData);

            //
            retrieveTableDesc(realCatalog, realSchema, tableName, table, databaseMetaData);

            retrieveColumns(realCatalog, realSchema, tableName, table, databaseMetaData);

            addPrimaryKey(realCatalog, realSchema, tableName, table, databaseMetaData);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        while (rs.next()) {
            addColumn(supportsIsAutoIncrement, supportsIsGeneratedColumn, rs, table);
        }
        JdbcUtils.closeResultSet(rs);
    }

    private void addPrimaryKey(String realCatalog, String realSchema, String tableName, Table table, DatabaseMetaData databaseMetaData) throws Exception {
        ResultSet rs = databaseMetaData.getPrimaryKeys(
                realCatalog, realSchema, tableName);
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME"); //$NON-NLS-1$
            table.addPrimaryKeyColumn(columnName);
        }
        if (CollectionUtils.isNotEmpty(table.getPrimaryKeyColumns())) {
            for (IntrospectedColumn introspectedColumn : table.getPrimaryKeyColumns()) {
                introspectedColumn.setIdentity(true);
            }
        }
        FullyQualifiedJavaType primaryJavaType = new FullyQualifiedJavaType("java.lang.Object");
        if (CollectionUtils.isNotEmpty(table.getPrimaryKeyColumns()) && table.getPrimaryKeyColumns().size() == 1) {
            IntrospectedColumn introspectedColumn = table.getPrimaryKeyColumns().get(0);
            if (!introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                primaryJavaType = introspectedColumn.getFullyQualifiedJavaType();
            }
        }
        table.setPrimaryJavType(primaryJavaType);

        JdbcUtils.closeResultSet(rs);

    }

    private void addColumn(boolean supportsIsAutoIncrement, boolean supportsIsGeneratedColumn, ResultSet rs, Table table) throws Exception {
        Column introspectedColumn = new Column();

        introspectedColumn.setJdbcType(rs.getInt("DATA_TYPE")); //$NON-NLS-1$

        introspectedColumn.setLength(rs.getInt("COLUMN_SIZE")); //$NON-NLS-1$

        introspectedColumn.setActualColumnName(rs.getString("COLUMN_NAME")); //$NON-NLS-1$

        introspectedColumn.setNullable(rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable); //$NON-NLS-1$

        introspectedColumn.setScale(rs.getInt("DECIMAL_DIGITS")); //$NON-NLS-1$

        introspectedColumn.setRemarks(rs.getString("REMARKS")); //$NON-NLS-1$

        introspectedColumn.setDefaultValue(rs.getString("COLUMN_DEF")); //$NON-NLS-1$

        FullyQualifiedJavaType fullyQualifiedJavaType = javaTypeResolver.calculateJavaType(introspectedColumn);

//        if (!fullyQualifiedJavaType.isPrimitive() && !fullyQualifiedJavaType.isArray()) {
//
//            table.addColumnType(fullyQualifiedJavaType);
//        }
        introspectedColumn.setFullyQualifiedJavaType(fullyQualifiedJavaType);

        introspectedColumn.setJdbcTypeName(javaTypeResolver.calculateJdbcTypeName(introspectedColumn));

        introspectedColumn.setJavaProperty(JavaBeansUtil.getCamelCaseString(introspectedColumn.getActualColumnName(), false));

        if (supportsIsAutoIncrement) {
            introspectedColumn.setAutoIncrement("YES".equals(rs.getString("IS_AUTOINCREMENT"))); //$NON-NLS-1$ //$NON-NLS-2$
        }
        TableDefine tableDefine = table.getTableDefine();

        if (supportsIsGeneratedColumn) {
            introspectedColumn.setGeneratedColumn("YES".equals(rs.getString("IS_GENERATEDCOLUMN"))); //$NON-NLS-1$ //$NON-NLS-2$
        }
        if (introspectedColumn.getActualColumnName().equals(tableDefine.getVersion())) {
            if (Types.NUMERIC == introspectedColumn.getJdbcType() ||
                    Types.DECIMAL == introspectedColumn.getJdbcType()) {
                introspectedColumn.setVersion(true);
                table.setVersion(true);
                table.setVersionColumn(introspectedColumn);
            }
        }

        if (Types.TIMESTAMP == introspectedColumn.getJdbcType()) {
            introspectedColumn.setDateTime(true);
        }

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
