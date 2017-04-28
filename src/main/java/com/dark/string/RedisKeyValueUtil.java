package com.dark.string;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * Created by dark on 2017/4/27.
 * 只包括key value 的操作. 使用了spring-data-redis 框架
 */
public class RedisKeyValueUtil {


    private RedisTemplate redisTemplate;

    public RedisKeyValueUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 缓存value操作
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     * @return
     */
    public void cacheValue(String key, String value, long expireTime) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, value);
        if (expireTime > 0) redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 缓存value操作
     *
     * @param key
     * @param value
     * @return
     */
    public void cacheValue(String key, String value) {
        cacheValue(key, value, -1);
    }

    /**
     * 判断是否存在
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        return valueOps.get(key);
    }


    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
