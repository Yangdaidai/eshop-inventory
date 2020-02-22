package com.young.eshop.inventory.util;


import org.apache.commons.lang3.StringUtils;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.util
 * @ClassName InventoryKeyUtils
 * @Description redis主键工具类
 * @Author young
 * @Modify young
 * @Date 2020/2/18 22:53
 * @Version 1.0.0
 **/
public class ProjectRedisKeyUtils {
    public static final String DEFAULT_INVENTORY_KEY_PREFIX = "inventory:";

    /**
     * 获取库存数据的redis key
     *
     * @param key 主键
     * @return java.lang.String
     */
    public static String getInventoryKey(String key) {
        return DEFAULT_INVENTORY_KEY_PREFIX + key;
    }

    /**
     * 获取库存数据的key
     *
     * @param key 主键
     * @return java.lang.String
     */
    public static String getInventoryKey(int key) {
        return getInventoryKey(String.valueOf(key));
    }

    /**
     * @return java.lang.String
     * @Description 获取库存数据的redis key
     * @param: key 主键
     * @param: prefix 前缀
     * @Author young
     * @CreatedTime 2020/2/22 23:39
     * @Version V1.0.0
     **/
    public static String getInventoryKeyWithPrefix(String key, final String prefix) {
        if (StringUtils.isNotEmpty(prefix))
            return prefix + key;
        else return getInventoryKey(key);
    }


}
