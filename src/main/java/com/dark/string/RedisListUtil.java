package com.dark.string;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis-simple
 * User: dark xue
 * Date: 2017/4/28
 * Time: 14:18
 * description: list 集合
 */
public class RedisListUtil {

    private RedisTemplate redisTemplate;

    public RedisListUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * list缓存
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public void cacheList(String key, String value, long expireTime) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.rightPush(key, value);
        if (expireTime > 0) redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 缓存list
     *
     * @param key
     * @param value
     * @return
     */
    public void cacheList(String key, String value) {
        cacheList(key, value, -1L);
    }

    /**
     * 缓存list
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public void cacheList(String key, List<String> value, long expireTime) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.rightPushAll(key, value);
        if (expireTime > 0) redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 缓存list
     *
     * @param key
     * @param value
     * @return
     */
    public void cacheList(String key, List<String> value) {
        cacheList(key, value, -1);
    }

    /**
     * 获取list缓存
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> getList(String key, long start, long end) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        return listOps.range(key, start, end);

    }

    /**
     * 获取总条数, 可用于分页
     *
     * @param key
     * @return
     */
    public long getListSize(String key) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        return listOps.size(key);

    }


    /**
     * 移除list缓存
     *
     * @param key
     * @return
     */
    public void removeOneOfList(String key) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.rightPop(key);
    }
}
