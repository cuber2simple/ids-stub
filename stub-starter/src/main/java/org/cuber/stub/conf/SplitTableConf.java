package org.cuber.stub.conf;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cuber.basic.facade.bridge.BizTableDefBridge;
import org.cuber.stub.StubConstant;
import org.cuber.stub.basic.BizTableDef;
import org.cuber.stub.ids.ZkIdGenerator;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.util.RpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class SplitTableConf {

    private static Logger logger = LoggerFactory.getLogger(SplitTableConf.class);
    @Autowired
    private ApplicationContext applicationContext;

    private static final ConcurrentHashMap<Object, ZkIdGenerator> idGenerators = new ConcurrentHashMap<>();

    @EventListener
    public void cachePrepare(ApplicationReadyEvent applicationReadyEvent) {
        if (!StubConstant.BASIC_SERVICE.equals(applicationContext.getApplicationName())) {
            BizTableDefBridge bizTableDefBridge = applicationContext.getBean(BizTableDefBridge.class);
            if (Objects.nonNull(bizTableDefBridge)) {
                Resp<List<BizTableDef>> resp = bizTableDefBridge.loadCacheByAppName(new Req<>(applicationContext.getApplicationName()));
                if (RpcUtils.isSuccess(resp)) {
                    List<BizTableDef> result = resp.getResult();
                    if (CollectionUtils.isNotEmpty(result)) {
                        result.forEach(bizTableDef -> {
                            ZkIdGenerator zkIdGenerator = new ZkIdGenerator(bizTableDef);
                            String tableName = bizTableDef.getTableName();
                            tableName = StringUtils.upperCase(StringUtils.trimToEmpty(tableName));
                            Class tClass = bizTableDef.gettClass();
                            idGenerators.putIfAbsent(tableName, zkIdGenerator);
                            idGenerators.putIfAbsent(tClass, zkIdGenerator);
                        });
                    }
                }
            } else {
                logger.warn(StubConstant.WITHOUT_BASIC_WARN);
            }
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
}
