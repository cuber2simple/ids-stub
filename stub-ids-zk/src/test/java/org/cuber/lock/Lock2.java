package org.cuber.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.cuber.ids.IdPattern;
import org.cuber.zk.ZkIdGenerator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;


public class Lock2 {

    /** zookeeper地址 */
    static final String CONNECT_ADDR = "127.0.0.1:2181";
    /** session超时时间 */
    static final int SESSION_OUTTIME = 5000;

    static int count = 10;
    public static void genarNo(){
        try {
            count--;
            System.out.println(count);
        } finally {

        }
    }

    public static void main(String[] args) throws Exception {

        //1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1);
        //2 通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_ADDR)
                .sessionTimeoutMs(SESSION_OUTTIME)
                .retryPolicy(retryPolicy)
//                    .namespace("super")
                .build();
        //3 开启连接
        cf.start();
        System.out.println(1234);
        //4 分布式锁
        ZkIdGenerator zkIdGenerator = new ZkIdGenerator();
        IdPattern idPattern = new IdPattern();
        idPattern.setNamespace("/test");
        long now = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i = 0; i < 1; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(zkIdGenerator.nextId(idPattern));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            },"t" + i).start();
        }
        Thread.sleep(100);
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - now);
    }
}
