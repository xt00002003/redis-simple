package com.dark.json;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * Created by dark on 2017/4/27.
 * 存储json对象. 使用了spring-data-redis 框架
 */
public class RedisKeyValueJsonUtil<T> {


    private RedisTemplate redisTemplate;

    public RedisKeyValueJsonUtil(RedisTemplate redisTemplate,Class<T> clazz) {
        this.redisTemplate = redisTemplate;
        //获取泛型的Class类型
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<T>(clazz));
    }

    /**
     * 缓存value操作
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     * @return
     */
    public void cacheValue(String key, T value, long expireTime) {
        ValueOperations<String, T> valueOps = redisTemplate.opsForValue();
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
    public void cacheValue(String key, T value) {
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
    public T getValue(String key) {
        ValueOperations<String, T> valueOps = redisTemplate.opsForValue();
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
