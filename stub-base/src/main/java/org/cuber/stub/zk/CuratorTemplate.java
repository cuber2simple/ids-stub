package org.cuber.stub.zk;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.cuber.stub.StubConstant;
import org.cuber.stub.json.GsonHolder;

import java.util.List;
import java.util.Objects;

public class CuratorTemplate {

    public static volatile CuratorFramework instance;

    private static final Logger logger = LogManager.getLogger(CuratorTemplate.class);

    static{
        String zkAddress = System.getenv(StubConstant.ZOOKEEPER_ADDRESS);
        if(StringUtils.isEmpty(zkAddress)){
            zkAddress = System.getProperty(StubConstant.ZOOKEEPER_ADDRESS);
            if(StringUtils.isEmpty(zkAddress)){
                logger.warn("在环境配置中没有[{}]配置,将使用默认[{}]",StubConstant.ZOOKEEPER_ADDRESS,StubConstant.ZOOKEEPER_ADDRESS_DEFAULT);
            }
            zkAddress = StubConstant.ZOOKEEPER_ADDRESS_DEFAULT;
        }
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        instance = CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(6000)
                .connectionTimeoutMs(3000)
                .build();
        instance.start();
    }



    public static <T> T readPath(String path,Class<T> tClass){
        try{
            Stat stat = instance.checkExists().forPath(path);
            if(Objects.nonNull(stat)){
                String json = new String(instance.getData().forPath(path),"utf-8");
                T t = GsonHolder.fromJson(json,tClass);
                return t;
            }
        }catch (Exception e){
            logger.error("操作zookeeper报错",e);
        }
        return null;
    }

    public static List<String> readSubPath(String path){
        try{
            Stat stat = instance.checkExists().forPath(path);
            if(Objects.nonNull(stat)){
                return instance.getChildren().forPath(path);
            }
        }catch (Exception e){
            logger.error("操作zookeeper报错",e);
        }
        return null;
    }

    public static void writePath(String path,Object obj){
        try{
            Stat stat = instance.checkExists().forPath(path);
            String json = GsonHolder.toJson(obj);
            if(Objects.nonNull(stat)){
                instance.setData()
                        .forPath(path,json.getBytes("utf-8"));
            }else{
                instance.create()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(path,json.getBytes("utf-8"));
            }
        }catch (Exception e){
            logger.error("操作zookeeper报错",e);
        }
    }
}
