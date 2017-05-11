package com.dark.string;

import com.dark.string.RedisKeyValueUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis-simple
 * User: dark xue
 * Date: 2017/4/28
 * Time: 14:00
 * description:
 */
public class RedisKeyValueTest {

    private RedisTemplate redisTemplate;
    private RedisKeyValueUtil redisKeyValueUtil;

    @Before
    public void init() throws Exception{
        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        redisTemplate=(RedisTemplate)applicationContext.getBean("redisTemplate");
        redisKeyValueUtil=new RedisKeyValueUtil(redisTemplate);
    }

    @Test
    public void addValue(){
        String key="hello";
        String value="world";
        redisKeyValueUtil.cacheValue(key,value);
    }
    @Test
    public void getValue(){
        String key="hello";
        String value=redisKeyValueUtil.getValue(key);
        System.out.println(value);
        Assert.assertEquals("need equlas","world",value);
    }
    @Test
    public void remove(){
        String key="hello";
        redisKeyValueUtil.remove(key);
    }

}
