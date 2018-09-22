package org.cuber.stub.conf;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cuber.anno.TableSplitStrategy;
import org.cuber.basic.facade.bridge.BizTableDefBridge;
import org.cuber.stub.StubConstant;
import org.cuber.stub.basic.BizTableDef;
import org.cuber.stub.ids.ZkIdGenerator;
import org.cuber.stub.interceptor.TrapParamInterceptor;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.util.DatePUtils;
import org.cuber.stub.util.RpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class SplitTableConf {

    private static Logger logger = LoggerFactory.getLogger(SplitTableConf.class);
    @Autowired
    private ApplicationContext applicationContext;

    private static final ConcurrentHashMap<Object, ZkIdGenerator> idGenerators = new ConcurrentHashMap<>();

    public static final String YYYY_MM = "yyyy_MM";

    @EventListener
    public void cachePrepare(ApplicationReadyEvent applicationReadyEvent) {
        try {
            if (!StubConstant.BASIC_SERVICE.equals(applicationContext.getApplicationName())) {
                BizTableDefBridge bizTableDefBridge = applicationContext.getBean(BizTableDefBridge.class);
                if (Objects.nonNull(bizTableDefBridge)) {
                    Resp<List<BizTableDef>> resp = bizTableDefBridge.loadCacheByAppName(new Req());
                    if (RpcUtils.isSuccess(resp)) {
                        List<BizTableDef> result = resp.getResult();
                        if (CollectionUtils.isNotEmpty(result)) {
                            result.forEach(bizTableDef -> {
                                ZkIdGenerator zkIdGenerator = new ZkIdGenerator(bizTableDef);
                                String tableName = bizTableDef.getTableName();
                                tableName = StringUtils.upperCase(StringUtils.trimToEmpty(tableName));
                                try {
                                    Class tClass = Class.forName(bizTableDef.getTClass());
                                    idGenerators.putIfAbsent(tClass, zkIdGenerator);
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                idGenerators.putIfAbsent(tableName, zkIdGenerator);

                            });
                        }
                    }
                } else {
                    logger.warn(StubConstant.WITHOUT_BASIC_WARN);
                }
            }
        } catch (Exception e) {
            logger.error("基本不会出错", e);
        }

    }

    public static String nextId(String tableName) {
        ZkIdGenerator zkIdGenerator = findByTableName(tableName);
        return zkIdGenerator.nextId();
    }

    public static String nextId(Class tClass) {
        ZkIdGenerator zkIdGenerator = findByTClass(tClass);
        return zkIdGenerator.nextId();
    }

    public static ZkIdGenerator findByTableName(String tableName) {
        String key = StringUtils.upperCase(StringUtils.trimToEmpty(tableName));
        return idGenerators.get(key);
    }

    public static boolean containsTClass(Class tClass) {
        return idGenerators.containsKey(tClass);
    }

    public static boolean containsTable(String table) {
        String key = StringUtils.upperCase(StringUtils.trimToEmpty(table));
        return idGenerators.containsKey(key);
    }


    public static ZkIdGenerator findByTClass(Class tClass) {
        return idGenerators.get(tClass);
    }

    public static LocalDateTime findIdTime(String tableName, String id) {
        ZkIdGenerator zkIdGenerator = findByTableName(tableName);
        return zkIdGenerator.findIdTime(id);
    }

    public static LocalDateTime findIdTime(Class tClass, String id) {
        ZkIdGenerator zkIdGenerator = findByTClass(tClass);
        return zkIdGenerator.findIdTime(id);
    }


    public static String getSplitPattern(String tableName) {
        ZkIdGenerator zkIdGenerator = findByTableName(tableName);
        return zkIdGenerator.getSplitPattern();
    }

    public static String getSplitPattern(Class tClass) {
        ZkIdGenerator zkIdGenerator = findByTClass(tClass);
        return zkIdGenerator.getSplitPattern();
    }

    public static boolean verify(TableSplitStrategy tableSplitStrategy) {
        boolean verify = false;
        if (Objects.nonNull(tableSplitStrategy)) {
            String[] tables = tableSplitStrategy.splitTables();
            verify = Arrays.stream(tables)
                    .anyMatch(table -> containsTable(table) && YYYY_MM.equals(getSplitPattern(table)));
        }
        return verify;
    }

    public static Date[] startAndEnd(TableSplitStrategy tableSplitStrategy) {
        StandardEvaluationContext standardEvaluationContext = TrapParamInterceptor.build();
        ExpressionParser parser = new SpelExpressionParser();
        Date date = new Date();
        Date[] startAndEnd = new Date[]{date, date};
        switch (tableSplitStrategy.strategy()) {
            case equals:
                LocalDateTime thatTime = parser.parseExpression(tableSplitStrategy.expl()).getValue(standardEvaluationContext, LocalDateTime.class);
                startAndEnd[0] = DatePUtils.trans2Date(thatTime);
                startAndEnd[1] = startAndEnd[0];
                break;
            case between:
                LocalDateTime begin = parser.parseExpression(tableSplitStrategy.bottomExpl()).getValue(standardEvaluationContext, LocalDateTime.class);
                LocalDateTime end = parser.parseExpression(tableSplitStrategy.topExpl()).getValue(standardEvaluationContext, LocalDateTime.class);
                startAndEnd[0] = DatePUtils.trans2Date(begin);
                startAndEnd[1] = DatePUtils.trans2Date(end);
                break;
            case untilNow:
                LocalDateTime backThen = parser.parseExpression(tableSplitStrategy.bottomExpl()).getValue(standardEvaluationContext, LocalDateTime.class);
                startAndEnd[0] = DatePUtils.trans2Date(backThen);
                break;
            default:
                break;
        }

        return startAndEnd;
    }

    public static void main(String[] args) {

    }


}
