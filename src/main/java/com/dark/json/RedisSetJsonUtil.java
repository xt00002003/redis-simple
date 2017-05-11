package com.dark.json;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by dark on 2017/4/27.
 */
public class RedisSetJsonUtil<T> {

    private RedisTemplate redisTemplate;

    public RedisSetJsonUtil(RedisTemplate redisTemplate,Class<T> clazz) {
        this.redisTemplate = redisTemplate;
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<T>(clazz));
    }

    /**
     * 缓存set
     *
     * @param key
     * @param values
     * @param expireTime
     * @return
     */
    public void cacheSet(String key, Set<T> values, long expireTime) {
        SetOperations<String, T> setOps = redisTemplate.opsForSet();
        setOps.add(key, values.toArray((T[]) new Object[values.size()]));
        if (expireTime > 0) redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);

    }

    /**
     * 缓存set
     * @param key
     * @param values
     * @return
     */
    public void cacheSet(String key, Set<T> values) {
        cacheSet(key, values, -1);
    }


    /**
     * 获取缓存set数据
     * @param key
     * @return
     */
    public Set<T> getSet(String key) {
        SetOperations<String, T> setOps = redisTemplate.opsForSet();
        return setOps.members(key);
    }

    /**
     * 删除Set集合中的字符元素
     * @param key
     * @param values
     */
    public void delMemberFromSet(String key,T... values){
        SetOperations<String, T> setOps = redisTemplate.opsForSet();
        setOps.remove(key,values);
    }

}
