package com.dark.json;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis-simple
 * User: dark xue
 * Date: 2017/4/28
 * Time: 15:52
 * description:
 */
public class RedisHashJsonUtil<T> {

    private RedisTemplate redisTemplate;

    public RedisHashJsonUtil(RedisTemplate redisTemplate,Class<T> clazz) {
        this.redisTemplate = redisTemplate;
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<T>(clazz));
    }

    /**
     * hash缓存
     *
     * @param hashKey
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public void cacheHash(String hashKey,String key, T value, long expireTime) {
        HashOperations<String,String,T> hashOperations=redisTemplate.opsForHash();
        hashOperations.put(hashKey,key,value);
        if (expireTime > 0) redisTemplate.expire(hashKey, expireTime, TimeUnit.SECONDS);
    }

    /**
     * hash缓存
     *
     * @param key
     * @param value
     * @return
     */
    public void cacheHash(String hashKey,String key, T value) {
        cacheHash(hashKey,key,value,-1L);
    }

    /**
     * hash缓存
     *
     * @param hashKey
     * @param maps
     * @param expireTime
     * @return
     */
    public void cacheHashMap(String hashKey, Map<String,T> maps, long expireTime) {
        HashOperations<String,String,T> hashOperations=redisTemplate.opsForHash();
        hashOperations.putAll(hashKey,maps);
        if (expireTime > 0) redisTemplate.expire(hashKey, expireTime, TimeUnit.SECONDS);
    }

    /**
     * hash缓存
     *
     * @param hashKey
     * @param maps
     * @return
     */
    public void cacheHashMap(String hashKey, Map<String,T> maps) {
        cacheHashMap(hashKey,maps,-1L);
    }

    /**
     * 获取HASH数据
     * @param hashKey
     * @return
     */
    public Map<String,T> getEntity(String hashKey){
        HashOperations<String,String,T> hashOperations=redisTemplate.opsForHash();
        return hashOperations.entries(hashKey);
    }

    /**
     * 删除Hash中的元素
     * @param hashKey
     * @param key
     */
    public void deleteEntity(String hashKey,String... key){
        HashOperations<String,String,T> hashOperations=redisTemplate.opsForHash();
        hashOperations.delete(hashKey,key);
    }



}
