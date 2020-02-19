package com.young.eshop.inventory.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * redis本身有各种各样的api和功能
 * 可以做出来很多很多非常花哨的功能
 *
 * @author Administrator
 */
public interface RedisDao {

    boolean set(String key, Object value);


    Object get(String key);

    boolean delete(String key);

    /**
     * 保存有实效时间的键值对
     *
     * @param key
     * @param value
     * @param expireTime 实效时间 单位毫秒
     * @return boolean
     */
    boolean set(String key, Object value, long expireTime);

    /**
     * 判断缓存中是否有对应的value
     */
    boolean exists(final String key);

    /**
     * 指定键缓存失效的时间
     *
     * @param key        指定的键
     * @param expireTime 超时时间 毫秒
     * @return
     */
    boolean expire(String key, long expireTime);

    /**
     * 哈希 添加
     */
    void hmSet(Serializable key, Object hashKey, Object value);

    /**
     * 哈希获取数据
     */
    Object hmGet(Serializable key, Object hashKey);

    /**
     * 列表添加
     */
    void lPush(Serializable k, Object v);

    /**
     * 列表获取
     */
    List<Object> lRange(Serializable k, long l, long l1);

    /**
     * 集合添加
     */
    void add(Serializable key, Object value);

    /**
     * 集合获取
     */
    Set<Object> setMembers(String key);

    /**
     * 有序集合添加
     */
    void zAdd(Serializable key, Object value, double score);

    /**
     * 有序集合获取
     */
    Set<Object> rangeByScore(Serializable key, double min, double max);
}
