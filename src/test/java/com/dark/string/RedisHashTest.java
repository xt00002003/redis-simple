package com.dark.string;

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
public class RedisHashTest {

    private RedisTemplate redisTemplate;

    private RedisHashUtil redisHashUtil;

    @Before
    public void init() throws Exception{
        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        redisTemplate=(RedisTemplate)applicationContext.getBean("redisTemplate");
        redisHashUtil=new RedisHashUtil(redisTemplate);
    }

    @Test
    public void addHashEntity(){
        Map<String,String> map=new HashMap<String, String>();
        map.put("test1","one");
        map.put("test2","two");
        map.put("test3","three");
        map.put("test4","four");
        redisHashUtil.cacheHashMap("hashTest1",map);
    }
    @Test
    public void getHashEntities(){
        String hashKey="hashTest1";
        Map<String,String> result=redisHashUtil.getEntity(hashKey);
        System.out.println(result);
    }

    @Test
    public void deleteEntity(){
        String hashKey="hashTest1";
        String key="test4";
        redisHashUtil.deleteEntity(hashKey,key);
    }
}
