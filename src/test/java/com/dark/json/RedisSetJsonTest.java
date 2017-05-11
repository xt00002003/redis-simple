package com.dark.json;

import com.dark.domain.UserInfo;
import com.dark.string.RedisHashUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * redis-simple
 * User: dark xue
 * Date: 2017/4/28
 * Time: 16:17
 * description:
 */
public class RedisSetJsonTest {

    private RedisTemplate redisTemplate;

    private RedisSetJsonUtil<UserInfo> redisSetJsonUtil;

    @Before
    public void init() throws Exception{
        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        redisTemplate=(RedisTemplate)applicationContext.getBean("redisTemplate");
        redisSetJsonUtil=new RedisSetJsonUtil<UserInfo>(redisTemplate,UserInfo.class);
    }

    @Test
    public void addHashEntity(){
        UserInfo userInfo=new UserInfo();
        userInfo.setPassword("123");
        userInfo.setUserName("test1");
        UserInfo userInfo2=new UserInfo();
        userInfo2.setPassword("456");
        userInfo2.setUserName("test2");
        Set<UserInfo> set=new HashSet<UserInfo>();
        set.add(userInfo);
        set.add(userInfo2);
        redisSetJsonUtil.cacheSet("testSetJson",set);
    }
    @Test
    public void getHashEntities(){
        String key="testSetJson";
        Set<UserInfo> result=redisSetJsonUtil.getSet(key);
        System.out.println(result);
    }

    @Test
    public void deleteEntity(){
        String key="testSetJson";
        redisSetJsonUtil.delMemberFromSet(key);
    }
}
