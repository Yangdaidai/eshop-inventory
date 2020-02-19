package com.young.eshop.inventory.dao.impl;

import com.young.eshop.inventory.dao.RedisDao;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisDaoImpl implements RedisDao {

    @Resource
    private RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @return boolean
     */
    @Override
    public boolean set(String key, Object value) {
        Objects.requireNonNull(key, "key不能为空!");
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 保存有实效时间的键值对
     *
     * @param key
     * @param value
     * @param expireTime 实效时间 单位毫秒
     * @return boolean
     */
    public boolean set(String key, Object value, long expireTime) {
        Objects.requireNonNull(key, "key不能为空!");
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value, expireTime, TimeUnit.MICROSECONDS);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return java.lang.Object
     */
    @Override
    public Object get(String key) {
        Objects.requireNonNull(key, "key不能为空!");
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            return operations.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除key删除对应的value
     *
     * @param key
     * @return boolean
     */
    public boolean delete(String key) {
        Objects.requireNonNull(key, "key不能为空!");
        try {
            redisTemplate.delete(key);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }


    /**
     * 判断缓存中是否有对应的value
     */
    public boolean exists(final String key) {
        Objects.requireNonNull(key, "key不能为空!");
        return redisTemplate.hasKey(key);
    }

    /**
     * 指定键缓存失效的时间
     *
     * @param key        指定的键
     * @param expireTime 超时时间 毫秒
     * @return
     */
    public boolean expire(String key, long expireTime) {
        Objects.requireNonNull(key, "key不能为空!");
        try {
            return redisTemplate.expire(key, expireTime, TimeUnit.MICROSECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * 哈希 添加
     */
    public void hmSet(Serializable key, Object hashKey, Object value) {
        HashOperations<Serializable, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     */
    public Object hmGet(Serializable key, Object hashKey) {
        HashOperations<Serializable, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     */
    public void lPush(Serializable k, Object v) {
        ListOperations<Serializable, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     */
    public List<Object> lRange(Serializable k, long l, long l1) {
        ListOperations<Serializable, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     */
    public void add(Serializable key, Object value) {
        SetOperations<Serializable, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     */
    public Set<Object> setMembers(String key) {
        SetOperations<Serializable, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     */
    public void zAdd(Serializable key, Object value, double score) {
        ZSetOperations<Serializable, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, score);
    }

    /**
     * 有序集合获取
     */
    public Set<Object> rangeByScore(Serializable key, double min, double max) {
        ZSetOperations<Serializable, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, min, max);
    }
}
