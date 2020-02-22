package com.young.eshop.inventory.service.impl;

import com.young.eshop.inventory.dao.RedisDao;
import com.young.eshop.inventory.mapper.InventoryMapper;
import com.young.eshop.inventory.model.Inventory;
import com.young.eshop.inventory.service.InventoryService;
import com.young.eshop.inventory.util.ProjectRedisKeyUtils;
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
        String key = ProjectRedisKeyUtils.getInventoryKey(String.valueOf(inventory.getId().intValue()));
        Integer inventoryCount = inventory.getInventoryCount();
        redisDao.set(key, String.valueOf(inventoryCount));
        log.info("===========双写日志===========: 设置库存缓存的请求已完成，商品id:{} , 库存数量:{} ", inventory.getId(), inventory.getInventoryCount());

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

        String key = ProjectRedisKeyUtils.getInventoryKey(productId);
        String result = (String) redisDao.get(key);
        if (StringUtils.isNotEmpty(result)) {
            Integer count = Integer.parseInt(result);
            return new Inventory(productId, null, count);
        }
        return null;
    }


    /**
     * @return void
     * @Description 删除商品库存的缓存
     * @param: inventory  商品库存
     * @Author young
     * @CreatedTime 2020/2/22 22:34
     * @Version V1.0.0
     **/
    @Override
    public void removeInventoryCache(Inventory inventory) {
        String key = ProjectRedisKeyUtils.getInventoryKey(String.valueOf(inventory.getId()));
        redisDao.delete(key);
        log.info("===========双写日志===========: 已删除redis中的缓存  key: {}", key);

    }

    /**
     * @return void
     * @Description 更新商品库存
     * @param: inventory
     * @Author young
     * @CreatedTime 2020/2/18 22:04
     * @Version V1.0.0
     **/
    @Override
    public void updateInventory(Inventory inventory) {
        inventoryMapper.updateByPrimaryKey(inventory);
        log.info("===========双写日志===========: 已更新数据库中的库存,商品库存id: {}, 商品库存数量:{}", inventory.getId(), inventory.getInventoryCount());
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
