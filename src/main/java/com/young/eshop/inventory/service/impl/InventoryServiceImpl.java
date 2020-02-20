package com.young.eshop.inventory.service.impl;

import com.young.eshop.inventory.dao.RedisDao;
import com.young.eshop.inventory.mapper.InventoryMapper;
import com.young.eshop.inventory.model.Inventory;
import com.young.eshop.inventory.service.InventoryService;
import com.young.eshop.inventory.util.InventoryKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.service.impl
 * @ClassName InventoryServiceImpl
 * @Description
 * @Author young
 * @Modify young
 * @Date 2020/2/18 22:02
 * @Version 1.0.0
 **/
@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    @Resource
    private InventoryMapper inventoryMapper;

    @Resource
    private RedisDao redisDao;

    /**
     * @return void
     * @Description 设置库存缓存
     * @param: inventory
     * @Author young
     * @CreatedTime 2020/2/18 22:03
     * @Version V1.0.0
     **/
    @Override
    public void setInventoryCache(Inventory inventory) {
        String key = InventoryKeyUtils.getInventoryKey(String.valueOf(inventory.getId()));
        Integer inventoryCount = inventory.getInventoryCount();
        redisDao.set(key, inventoryCount);
    }

    /**
     * @return com.young.eshop.inventory.model.Inventory
     * @Description 获取商品库存的缓存
     * @param: productId
     * @Author young
     * @CreatedTime 2020/2/19 17:45
     * @Version V1.0.0
     */
    @Override
    public Inventory getInventoryCache(Integer productId) {
        Integer inventoryCount = 0;

        String key = InventoryKeyUtils.getInventoryKey(productId);
        String result = (String) redisDao.get(key);

        if (StringUtils.isNotEmpty(result)) {
            try {
                inventoryCount = Integer.valueOf(result);
                return new Inventory(productId, null, inventoryCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * @return void
     * @Description 删除设置库存缓存
     * @param: inventory
     * @Author young
     * @CreatedTime 2020/2/18 22:04
     * @Version V1.0.0
     **/
    @Override
    public void removeInventoryCache(Inventory inventory) {
        String key = InventoryKeyUtils.getInventoryKey(String.valueOf(inventory.getId()));
        redisDao.delete(key);
    }

    /**
     * @return void
     * @Description 更新设置库存
     * @param: inventory
     * @Author young
     * @CreatedTime 2020/2/18 22:04
     * @Version V1.0.0
     **/
    @Override
    public void updateInventory(Inventory inventory) {
        inventoryMapper.updateByPrimaryKey(inventory);
    }

    /**
     * @return com.young.eshop.inventory.model.Inventory
     * @Description 根据库存id查询库存
     * @param: id 商品库存id
     * @Author young
     * @CreatedTime 2020/2/18 22:05
     * @Version V1.0.0
     **/
    @Override
    public Inventory findInventory(Integer id) {
        return inventoryMapper.selectByPrimaryKey(id);
    }
}
