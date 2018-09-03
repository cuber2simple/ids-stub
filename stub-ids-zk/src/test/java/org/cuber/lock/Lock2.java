package org.cuber.lock;

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
}
