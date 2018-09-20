package org.cuber.stub.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.cuber.anno.TableSplitStrategy;
import org.cuber.stub.util.RefUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Objects;
import java.util.Properties;
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
        MappedStatement mappedStatement = findMappedStatement(statementHandler);

    }

    public static boolean isSplit(MappedStatement mappedStatement){
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
        if (Objects.nonNull(tableSplitStrategy)) {
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
            TableSplitStrategy tableSplitStrategy = loadClassStrategy(classId);
            String methodName = StringUtils.substringAfterLast(sqlId, ".");
            Method method = ReflectionUtils.findMethod(Class.forName(classId), methodName);
            /**
             * 避开分页自己插入的SQL ID
             */
            if (Objects.isNull(method)) {
                methodName = StringUtils.substringBeforeLast(methodName, "_COUNT");
                method = ReflectionUtils.findMethod(Class.forName(classId), methodName);
            }
            if (Objects.nonNull(method)) {
                TableSplitStrategy tableSplitStrategyMethod = method.getDeclaredAnnotation(TableSplitStrategy.class);
                if (Objects.nonNull(tableSplitStrategyMethod)) {
                    tableSplitStrategy = tableSplitStrategyMethod;
                }
            }
            String trueSqlId = classId + "." + methodName;
            sqlIdMappingStrategy.putIfAbsent(trueSqlId, tableSplitStrategy);
            sqlIdMappingStrategy.putIfAbsent(sqlId, tableSplitStrategy);
        } catch (Exception e) {
            logger.error("不是正确的类名L", e);
        }
        return null;
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
