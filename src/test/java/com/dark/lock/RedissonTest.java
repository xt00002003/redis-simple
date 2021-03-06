package com.dark.lock;

import com.dark.lock.RedissionUtils;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;

/**
 * Created by Administrator on 2017/4/26.
 */
public class RedissonTest {

    private RedissonClient redissonClient;

    /**
     * 初始化redis
     * @throws Exception
     */
    @Before
    public void init() throws Exception{
        Config config = new Config();
        SentinelServersConfig sentinelServersConfig=config.useSentinelServers();
        sentinelServersConfig.addSentinelAddress("10.8.48.234:26379","10.8.48.234:26380","10.8.48.234:26381");
        sentinelServersConfig.setMasterName("tomcat-dev");
        sentinelServersConfig.setDatabase(2);
        redissonClient = RedissionUtils.getInstance().getRedisson(config);
    }


    @Test
    public void test() throws InterruptedException {
        //redisson配置
//        Config config = new Config();
//        SingleServerConfig singleSerververConfig = config.useSingleServer();
//        singleSerververConfig.setAddress("127.0.0.1:6379");
//        singleSerververConfig.setPassword("redis");
//
//        RedissonClient redissonClient = RedissionUtils.getInstance().getRedisson(config);
//        RBucket<Object> rBucket = RedissionUtils.getInstance().getRBucket(redissonClient, "key");
//        //rBucket.set("wangnian");
//        System.out.println(rBucket.get());
//
//        while (true) {
//            RLock lock = redissonClient.getLock("lock");
//            lock.tryLock(0, 1, TimeUnit.SECONDS);//第一个参数代表等待时间，第二是代表超过时间释放锁，第三个代表设置的时间制
//            try {
//                System.out.println("执行");
//            } finally {
//                lock.unlock();
//            }
//        }
    }

    private void processBusiness() throws Exception{
        RLock lock=redissonClient.getLock("tms-lock");
        try {
            //获取锁
            lock.lock();
            //执行业务逻辑

            System.out.println("当前执行线程名称是："+Thread.currentThread().getName());
            System.out.println("-------------------执行业务逻辑代码-------------------");
            Thread.sleep(2000);
            System.out.println("-------------------业务逻辑代码执行结束-------------------");
        }finally {
            lock.unlock();
        }
    }
    @Test
    public void testLock()throws Exception{
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                public void run() {
                    try {
                        processBusiness();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        System.in.read();

    }

}
