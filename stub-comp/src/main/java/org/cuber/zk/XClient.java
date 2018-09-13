package org.cuber.zk;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.cuber.stub.StubConstant;
import org.cuber.stub.json.GsonHolder;
import org.cuber.stub.utils.HardwareUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class XClient {
    private volatile static CuratorFramework curatorFramework;
    private static Logger logger = LoggerFactory.getLogger(XClient.class);
    private static AtomicBoolean connect = new AtomicBoolean(false);
    private static String LOCAL_SIGNATURE_WITHOUT_PREFIX;
    private static String LOCAL_SIGNATURE;
    static {
        try {
            LOCAL_SIGNATURE_WITHOUT_PREFIX = HardwareUtils.signatureLocal();
            LOCAL_SIGNATURE = StubConstant.CENTER_PATH + StubConstant.PATH_SPLIT + LOCAL_SIGNATURE_WITHOUT_PREFIX;
        } catch (Exception e) {
            LOCAL_SIGNATURE = null;
            LOCAL_SIGNATURE_WITHOUT_PREFIX = null;
        }
    }
    public static CuratorFramework getCuratorFramework() {
        if (Objects.isNull(curatorFramework)) {
            synchronized (logger) {
                String zkAddress = System.getenv(StubConstant.ZOOKEEPER_ADDRESS);
                if (StringUtils.isEmpty(zkAddress)) {
                    zkAddress = System.getProperty(StubConstant.ZOOKEEPER_ADDRESS);
                    if (StringUtils.isEmpty(zkAddress)) {
                        logger.warn("在环境配置中没有[{}]配置,将使用默认[{}]", StubConstant.ZOOKEEPER_ADDRESS, StubConstant.ZOOKEEPER_ADDRESS_DEFAULT);
                    }
                    zkAddress = StubConstant.ZOOKEEPER_ADDRESS_DEFAULT;
                }
                RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
                curatorFramework = CuratorFrameworkFactory.builder()
                        .connectString(zkAddress)
                        .retryPolicy(retryPolicy)
                        .sessionTimeoutMs(6000)
                        .connectionTimeoutMs(300)
                        .build();
                curatorFramework.getConnectionStateListenable().addListener((curatorFramework, connectionState) -> {
                    connect.set(connectionState.isConnected());
                });
                curatorFramework.start();
            }
        }
        return curatorFramework;
    }
    public static boolean isConnect() {
        return connect.get();
    }
    public static <T> T readPath(String path, Class<T> tClass) {
        try {
            Stat stat = getCuratorFramework().checkExists().forPath(path);
            if (Objects.nonNull(stat)) {
                String json = new String(getCuratorFramework().getData().forPath(path), "utf-8");
                T t = GsonHolder.fromJson(json, tClass);
                return t;
            }
        } catch (Exception e) {
            logger.error("操作zookeeper报错", e);
        }
        return null;
    }
    public static List<String> readSubPath(String path) {
        try {
            Stat stat = getCuratorFramework().checkExists().forPath(path);
            if (Objects.nonNull(stat)) {
                return getCuratorFramework().getChildren().forPath(path);
            }
        } catch (Exception e) {
            logger.error("操作zookeeper报错", e);
        }
        return null;
    }
    public static void writePath(String path, Object obj) {
        try {
            Stat stat = getCuratorFramework().checkExists().forPath(path);
            String json = GsonHolder.toJson(obj);
            if (Objects.nonNull(stat)) {
                getCuratorFramework().setData()
                        .forPath(path, json.getBytes("utf-8"));
            } else {
                getCuratorFramework().create()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(path, json.getBytes("utf-8"));
            }
        } catch (Exception e) {
            logger.error("操作zookeeper报错", e);
        }
    }

    public static long getLocalId() throws Exception {
        List<String> subs = readSubPath(StubConstant.CENTER_PATH);
        String target = null;
        if (!CollectionUtils.isEmpty(subs)) {
            Optional<String> str = subs.stream().filter(s -> s.startsWith(LOCAL_SIGNATURE_WITHOUT_PREFIX)).findFirst();
            target = str.orElse(null);
        }
        if (Objects.isNull(target)) {
            target = XClient
                    .getCuratorFramework().create()
                    .creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(LOCAL_SIGNATURE, null);
        }
        long returnValue = Long.parseLong(StringUtils.remove(target, LOCAL_SIGNATURE_WITHOUT_PREFIX));
        return returnValue;
    }
}
