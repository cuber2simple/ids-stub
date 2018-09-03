package org.cuber.ids;

import org.apache.commons.collections4.CollectionUtils;
import org.cuber.zk.XCuratorFramework;
import org.cuber.zk.ZkIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class IdGeneratorFactory {
    private static Logger logger = LoggerFactory.getLogger(IdGeneratorFactory.class);
    private static long LOCAL_ID = -1l;
    private static ConcurrentHashMap<Class, IdGenerator> map = new ConcurrentHashMap<>();

    public static long _localId() {
        if (-1l == LOCAL_ID) {
            try {
                LOCAL_ID = XCuratorFramework.getLocalId();
            } catch (Exception e) {
                LOCAL_ID = 0l;
                logger.error("得到本地的数据中心错误", e);
            }
        }
        return LOCAL_ID;
    }

    public static void load(List<IdPattern> ids){
        if(CollectionUtils.isNotEmpty(ids)){
            ids.stream().forEach(idPattern ->
                map.putIfAbsent(idPattern.getDtoClass(),new ZkIdGenerator(idPattern))
            );
        }
    }

    public static IdGenerator findByClass(Class tClass){
        return map.get(tClass);
    }

}
