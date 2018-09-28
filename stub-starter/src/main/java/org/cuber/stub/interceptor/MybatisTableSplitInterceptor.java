package org.cuber.stub.interceptor;

import com.github.pagehelper.util.MetaObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.cuber.anno.TableSplitStrategy;
import org.cuber.stub.conf.SplitTableConf;
import org.cuber.stub.util.DatePUtils;
import org.cuber.stub.util.RefUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class MybatisTableSplitInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(MybatisTableSplitInterceptor.class);

    private static final ConcurrentHashMap<String, TableSplitStrategy> sqlIdMappingStrategy = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, MybatisTableSplitStrategy> sqlIdMappingMybatisStrategy = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MappedStatement mappedStatement = findMappedStatement(statementHandler);
        MybatisTableSplitStrategy tableSplitStrategy = findTableStrategy(mappedStatement);
        if (Objects.nonNull(tableSplitStrategy) && tableSplitStrategy.isSplit()) {
            rebuild(statementHandler, tableSplitStrategy.getTableSplitStrategy());
        }
        return invocation.proceed();
    }

    private void rebuild(StatementHandler statementHandler, TableSplitStrategy splitStrategy) {
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        if (SplitTableConf.verify(splitStrategy)) {
            try {
                Date[] startAndEnd = SplitTableConf.startAndEnd(splitStrategy);
                boolean continueFlag = true;
                List<String> sqlList = new ArrayList<>();
                List<ParameterMapping> newParameterMappings = new ArrayList<>();
                while (continueFlag) {
                    String pattern = DateFormatUtils.format(startAndEnd[0], SplitTableConf.YYYY_MM);
                    String[] tables = splitStrategy.splitTables();
                    String sqlTmp = sql;
                    for (String table : tables
                            ) {
                        String tmp = StringUtils.upperCase(table);
                        String replaceTmp = tmp + "_" + pattern;
                        sqlTmp = sql.replaceAll(tmp, replaceTmp);
                    }
                    sqlList.add(sqlTmp);
                    continueFlag = startAndEnd[0].before(startAndEnd[1]) && !DatePUtils.isSameMonth(startAndEnd[0], startAndEnd[1]);
                    if (boundSql != null && boundSql.getParameterMappings() != null) {
                        newParameterMappings.addAll(boundSql.getParameterMappings());
                    }
                    startAndEnd[0] = DateUtils.addMonths(startAndEnd[0], 1);
                }
                String newSql = StringUtils.join(sqlList, " union all ");
                Field field = ReflectionUtils.findField(BoundSql.class, "sql");
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, boundSql, newSql);
                MetaObject metaObject = MetaObjectUtil.forObject(boundSql);
                metaObject.setValue("parameterMappings", newParameterMappings);
            } catch (Exception e) {
                logger.error("发生错误", e);
                logger.warn("发生错误,将不变更SQL执行下去");
            }
        }
    }


    public static boolean isSplit(MappedStatement mappedStatement) {
        MybatisTableSplitStrategy tableSplitStrategy = findTableStrategy(mappedStatement);
        return Objects.nonNull(tableSplitStrategy) && tableSplitStrategy.isSplit();
    }

    private static MybatisTableSplitStrategy findTableStrategy(MappedStatement mappedStatement) {
        MybatisTableSplitStrategy mybatisTableSplitStrategy = null;
        if (mappedStatement != null) {
            String sqlId = mappedStatement.getId();
            if (sqlIdMappingMybatisStrategy.containsKey(sqlId)) {
                mybatisTableSplitStrategy = sqlIdMappingMybatisStrategy.get(sqlId);
            } else {
                mybatisTableSplitStrategy = build2Mapping(sqlId);
            }
        }
        return mybatisTableSplitStrategy;
    }

    private static MybatisTableSplitStrategy build2Mapping(String sqlId) {
        TableSplitStrategy tableSplitStrategy = sqlIdMappingStrategy.get(sqlId);
        if (Objects.isNull(tableSplitStrategy)) {
            tableSplitStrategy = build2AnnoMapping(sqlId);
        }
        MybatisTableSplitStrategy mybatisTableSplitStrategy = new MybatisTableSplitStrategy();
        mybatisTableSplitStrategy.setSqlId(sqlId);
        mybatisTableSplitStrategy.setSplit(Objects.nonNull(tableSplitStrategy));
        mybatisTableSplitStrategy.setTableSplitStrategy(tableSplitStrategy);
        sqlIdMappingMybatisStrategy.putIfAbsent(sqlId, mybatisTableSplitStrategy);
        return sqlIdMappingMybatisStrategy.get(sqlId);
    }

    private static TableSplitStrategy build2AnnoMapping(String sqlId) {
        try {

            String classId = StringUtils.substringBeforeLast(sqlId, ".");
            Class target = Class.forName(classId);
            TableSplitStrategy tableSplitStrategy = loadClassStrategy(classId);
            String methodName = StringUtils.substringAfterLast(sqlId, ".");
            Method method = RefUtils.findFirstNameMatch(target, methodName);
            /**
             * 避开分页自己插入的SQL ID
             */
            if (Objects.isNull(method)) {
                methodName = StringUtils.substringBeforeLast(methodName, "_COUNT");
                method = RefUtils.findFirstNameMatch(target, methodName);
            }
            if (Objects.nonNull(method)) {
                TableSplitStrategy tableSplitStrategyMethod = method.getDeclaredAnnotation(TableSplitStrategy.class);
                if (Objects.nonNull(tableSplitStrategyMethod)) {
                    tableSplitStrategy = tableSplitStrategyMethod;
                }
            }
            String trueSqlId = classId + "." + methodName;
            if (Objects.nonNull(tableSplitStrategy)) {
                sqlIdMappingStrategy.putIfAbsent(trueSqlId, tableSplitStrategy);
                sqlIdMappingStrategy.putIfAbsent(sqlId, tableSplitStrategy);
            }
        } catch (Exception e) {
            logger.error("不是正确的类名L", e);
        }
        return sqlIdMappingStrategy.get(sqlId);
    }

    private static TableSplitStrategy loadClassStrategy(String classId) {
        TableSplitStrategy tableSplitStrategy = null;
        try {
            tableSplitStrategy = Class.forName(classId).getAnnotation(TableSplitStrategy.class);
        } catch (Exception e) {
            logger.error("不是正确的类名L" + classId, e);
        }
        return tableSplitStrategy;
    }

    private static MappedStatement findMappedStatement(StatementHandler statementHandler) {
        return RefUtils.findFieldValue(statementHandler, "mappedStatement", MappedStatement.class);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
