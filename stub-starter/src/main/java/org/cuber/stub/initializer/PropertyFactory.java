package org.cuber.stub.initializer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.cuber.basic.AppDef;
import org.cuber.stub.StubConstant;
import org.cuber.stub.conf.Property;
import org.cuber.stub.json.GsonHolder;
import org.cuber.zk.XClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MapPropertySource;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PropertyFactory {

    public static final String ENV_ROOT_PATH = "/_ENV";

    public static final String PATH_SPLIT = "/";

    private static ConcurrentHashMap<String, Object> applicationProperties = new ConcurrentHashMap<>();

    public static final List<String> listenerSets = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(PropertyFactory.class);

    public static void resetProperty(String name, Object value) {
        applicationProperties.put(name, value);
    }

    public static MapPropertySource loadFormZk(String appName) {

        String appPath = ENV_ROOT_PATH + PATH_SPLIT + appName;
        boolean isSelf = !StubConstant.COMMON.equals(appName);
        MapPropertySource mapPropertySource = null;
        try {
            CuratorFramework zkClient = XClient.getCuratorFramework();
            Map<String, Object> propMap = new HashMap<>();
            Stat stat = zkClient.checkExists().forPath(appPath);
            if (Objects.nonNull(stat)) {
                List<String> properties = zkClient.getChildren().forPath(appPath);
                if (CollectionUtils.isNotEmpty(properties)) {
                    properties.stream().forEach(propPath -> dealWithPropSubPath(appPath + PATH_SPLIT + propPath, propMap, isSelf));
                }
                if (isSelf) {
                    AppDef appDef = XClient.readPath(appPath, AppDef.class);
                    if (Objects.nonNull(appDef)) {
                        loadAppFixed(appDef, propMap);
                    }
                    applicationProperties.putAll(propMap);
                    mapPropertySource = new MapPropertySource(appName, applicationProperties);
                } else {
                    mapPropertySource = new MapPropertySource(appName, propMap);
                }
            }
        } catch (Exception e) {
            logger.error("加载属性出错!~", e);
        }
        return mapPropertySource;
    }

    private static void loadAppFixed(AppDef appDef, Map<String, Object> propMap) {
        propMap.put(StubConstant.APP_FIXED_CONTEXT_PATH, appDef.getContextPath());
        propMap.put(StubConstant.APP_FIXED_DESC, appDef.getAppDesc());
        propMap.put(StubConstant.APP_FIXED_GROUP, appDef.getGroupName());
        propMap.put(StubConstant.APP_FIXED_KAFKA_TOPIC, appDef.getKafkaTopic());
        propMap.put(StubConstant.APP_FIXED_DUBBO_PORT, appDef.getDubboPort());
        propMap.put(StubConstant.APP_FIXED_SERVER_PORT, appDef.getServerPort());
        propMap.put(StubConstant.APP_FIXED_USERID, appDef.getOwnUserId());
    }

    private static void dealWithPropSubPath(String path, Map<String, Object> propMap, boolean isListener) {
        try {
            CuratorFramework zkClient = XClient.getCuratorFramework();
            String jsonStr = new String(zkClient.getData().forPath(path), "utf-8");
            if (StringUtils.isNotEmpty(jsonStr)) {
                Property prop = GsonHolder.fromJson(jsonStr, Property.class);
                if (Objects.nonNull(prop)) {
                    propMap.put(prop.getName(), prop.getValue());
                    logger.debug("属性名{}({})值为{}加载到系统环境中", prop.getName(), prop.getDesc(), prop.getValue());
                    if (isListener) {
                        listenerSets.add(path);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("转化出错", e);
        }
    }

}
