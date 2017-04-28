package com.dark.string;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis-simple
 * User: dark xue
 * Date: 2017/4/28
 * Time: 15:52
 * description:
 */
public class RedisHashUtil {

    private RedisTemplate redisTemplate;

    public RedisHashUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
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
    public void cacheHash(String hashKey,String key, String value, long expireTime) {
        HashOperations<String,String,String> hashOperations=redisTemplate.opsForHash();
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
    public void cacheHash(String hashKey,String key, String value) {
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
    public void cacheHashMap(String hashKey, Map<String,String> maps, long expireTime) {
        HashOperations<String,String,String> hashOperations=redisTemplate.opsForHash();
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
    public void cacheHashMap(String hashKey, Map<String,String> maps) {
        cacheHashMap(hashKey,maps,-1L);
    }

    /**
     * 获取HASH数据
     * @param hashKey
     * @return
     */
    public Map<String,String> getEntity(String hashKey){
        HashOperations<String,String,String> hashOperations=redisTemplate.opsForHash();
        return hashOperations.entries(hashKey);
    }

    /**
     * 删除Hash中的元素
     * @param hashKey
     * @param key
     */
    public void deleteEntity(String hashKey,String... key){
        HashOperations<String,String,String> hashOperations=redisTemplate.opsForHash();
        hashOperations.delete(hashKey,key);
    }



}
