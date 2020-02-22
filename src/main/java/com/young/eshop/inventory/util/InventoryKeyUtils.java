package com.young.eshop.inventory.util;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.util
 * @ClassName RedisConnectionUtils
 * @Description
 * @Author young
 * @Modify young
 * @Date 2020/2/18 22:53
 * @Version 1.0.0
 **/
public class InventoryKeyUtils {
    public static final String DEFAULT_INVENTORY_KEY_PREFIX = "inventory:";

    /**
     * 获取库存数据的key
     *
     * @param key
     * @return
     */
    public static String getInventoryKey(String key) {
        return DEFAULT_INVENTORY_KEY_PREFIX + key;
    }

    /**
     * 获取库存数据的key
     *
     * @param key
     * @return
     */
    public static String getInventoryKey(int key) {
        return getInventoryKey(String.valueOf(key));
    }
}
