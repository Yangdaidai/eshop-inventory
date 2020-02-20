package com.young.eshop.inventory.request.impl;


import com.young.eshop.inventory.model.Inventory;
import com.young.eshop.inventory.request.Request;
import com.young.eshop.inventory.service.InventoryService;

/**
 * 重新加载商品库存的缓存
 *
 * @author Administrator
 */
public class InventoryCacheRequest implements Request {

    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 是否强制刷新
     */
    private boolean foreRefresh;
    /**
     * 商品库存Service
     */
    private InventoryService inventoryService;

    public InventoryCacheRequest(Integer productId,
                                 InventoryService inventoryService
            , boolean foreRefresh) {
        this.productId = productId;
        this.inventoryService = inventoryService;
        this.foreRefresh = foreRefresh;
    }

    @Override
    public void process() {
        // 从数据库中查询最新的商品库存数量
        Inventory productInventory = inventoryService.findInventory(productId);
        // 将最新的商品库存数量，刷新到redis缓存中去
        inventoryService.setInventoryCache(productInventory);
    }

    public Integer getInventoryId() {
        return productId;
    }

    @Override
    public boolean isForceFresh() {
        return foreRefresh;
    }

}

