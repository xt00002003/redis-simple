package com.dark;

import com.dark.domain.UserInfo;
import com.dark.json.RedisKeyValueJsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis-simple
 * User: dark xue
 * Date: 2017/4/28
 * Time: 15:11
 * description:
 */
public class RedisKeyValueJsonTest {


    private RedisTemplate redisTemplate;
    private RedisKeyValueJsonUtil<UserInfo> redisKeyValueJsonUtil;

    @Before
    public void init() throws Exception{
        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        redisTemplate=(RedisTemplate)applicationContext.getBean("redisTemplate");
        redisKeyValueJsonUtil=new RedisKeyValueJsonUtil<UserInfo>(redisTemplate,UserInfo.class);
    }

    @Test
    public void addUserInfo(){
        UserInfo userInfo=new UserInfo();
        userInfo.setUserName("dark");
        userInfo.setPassword("you know!");
        String key="json";
        redisKeyValueJsonUtil.cacheValue(key,userInfo);

    }
    @Test
    public void getUserInfo(){
        String key="json";
        UserInfo userInfo=redisKeyValueJsonUtil.getValue(key);
        System.out.println(userInfo.toString());
    }
}
