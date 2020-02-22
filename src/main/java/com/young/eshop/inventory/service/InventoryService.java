package com.young.eshop.inventory.service;

import com.young.eshop.inventory.model.Inventory;

/**
 * 商品库存管理接口
 */
public interface InventoryService {

    void setInventoryCache(Inventory inventory);

    /**
     * @return com.young.eshop.inventory.model.Inventory
     * @Description 获取商品库存的缓存
     * @param: productId
     * @Author young
     * @CreatedTime 2020/2/19 17:45
     * @Version V1.0.0
     **/
    Inventory getInventoryCache(Integer productId);

    void removeInventoryCache(Inventory inventory);

    void updateInventory(Inventory inventory);

    Inventory findInventory(Integer productId);
}
