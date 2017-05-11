package com.dark.json;

import com.dark.domain.UserInfo;
import com.dark.json.RedisHashJsonUtil;
import com.dark.string.RedisHashUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * redis-simple
 * User: dark xue
 * Date: 2017/4/28
 * Time: 16:17
 * description:
 */
public class RedisHashJsonTest {

    private RedisTemplate redisTemplate;

    private RedisHashJsonUtil redisHashJsonUtil;


    @Before
    public void init() throws Exception{
        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        redisTemplate=(RedisTemplate)applicationContext.getBean("redisTemplate");
        redisHashJsonUtil=new RedisHashJsonUtil(redisTemplate, UserInfo.class);
    }

    @Test
    public void addHashEntity(){
        UserInfo userInfo=new UserInfo();
        userInfo.setPassword("123");
        userInfo.setUserName("test1");
        UserInfo userInfo2=new UserInfo();
        userInfo2.setPassword("456");
        userInfo2.setUserName("test2");
        Map<String,UserInfo> map=new HashMap<String, UserInfo>();
        map.put("test1",userInfo);
        map.put("test2",userInfo2);
        redisHashJsonUtil.cacheHashMap("hashTest2",map);
    }
    @Test
    public void getHashEntities(){
        String hashKey="hashTest2";
        Map<String,UserInfo> result=redisHashJsonUtil.getEntity(hashKey);
        System.out.println(result);
    }

    @Test
    public void deleteEntity(){
        String hashKey="hashTest2";
        String key="test1";
        redisHashJsonUtil.deleteEntity(hashKey,key);
    }
}
