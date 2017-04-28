package com.dark.string;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by dark on 2017/4/27.
 */
public class RedisSetUtil {

    private RedisTemplate redisTemplate;

    public RedisSetUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 缓存set
     *
     * @param key
     * @param values
     * @param expireTime
     * @return
     */
    public void cacheSet(String key, Set<String> values, long expireTime) {
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        setOps.add(key, values.toArray(new String[values.size()]));
        if (expireTime > 0) redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);

    }

    /**
     * 缓存set
     * @param key
     * @param values
     * @return
     */
    public void cacheSet(String key, Set<String> values) {
        cacheSet(key, values, -1);
    }


    /**
     * 获取缓存set数据
     * @param key
     * @return
     */
    public Set<String> getSet(String key) {
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        return setOps.members(key);
    }

    /**
     * 删除Set集合中的字符元素
     * @param key
     * @param values
     */
    public void delMemberFromSet(String key,String... values){
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        setOps.remove(key,values);
    }

}
