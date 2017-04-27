package com.dark;

import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RLock;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;

/**
 * Created by Administrator on 2017/4/26.
 */
public class RedisLockTest {


    private RedisTemplate redisTemplate;

    @Before
    public void init() throws Exception{
        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        redisTemplate=(RedisTemplate)applicationContext.getBean("redisTemplate");
    }

    @Test
    public void testLock() throws Exception {
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

    private void processBusiness() throws Exception{
        RedisLock lock = new RedisLock(redisTemplate, "tms-lock",60000,60000);
        try {
            //获取锁
            boolean isLock=lock.lock();
            if(isLock){
                //执行业务逻辑
                System.out.println("当前执行线程名称是："+Thread.currentThread().getName());
                System.out.println("-------------------执行业务逻辑代码-------------------");
                Thread.sleep(2000);
                System.out.println("-------------------业务逻辑代码执行结束-------------------");
            }
        }finally {
            lock.unlock();
        }
    }




}
