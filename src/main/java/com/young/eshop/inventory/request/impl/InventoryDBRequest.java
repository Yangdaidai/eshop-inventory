package com.young.eshop.inventory.request.impl;

import com.young.eshop.inventory.model.Inventory;
import com.young.eshop.inventory.request.Request;
import com.young.eshop.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;

/**
 * 比如说一个商品发生了交易，那么就要修改这个商品对应的库存
 * <p>
 * 此时就会发送请求过来，要求修改库存，那么这个可能就是所谓的data update request，数据更新请求
 * <p>
 * cache aside pattern
 * <p>
 * （1）删除缓存
 * （2）更新数据库
 *
 * @author Administrator
 */
@Slf4j
public class InventoryDBRequest implements Request {

    /**
     * 商品库存
     */
    private Inventory inventory;
    /**
     * 商品库存Service
     */
    private InventoryService inventoryService;

    public InventoryDBRequest(Inventory inventory,
                              InventoryService inventoryService) {
        this.inventory = inventory;
        this.inventoryService = inventoryService;
    }

    @Override
    public void process() {
        // 删除redis中的缓存
        inventoryService.removeInventoryCache(inventory);
//        为了模拟演示先删除了redis中的缓存，然后还没更新数据库的时候，读请求过来了，这里可以人工sleep一下
//        try {
//            Thread.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // 修改数据库中的库存
        inventoryService.updateInventory(inventory);
    }

    /**
     * 获取商品id
     */
    @Override
    public Integer getInventoryId() {
        return inventory.getId();
    }

}
