package com.young.eshop.inventory.controller;

import com.young.eshop.inventory.model.Inventory;
import com.young.eshop.inventory.request.Request;
import com.young.eshop.inventory.request.impl.InventoryCacheRequest;
import com.young.eshop.inventory.request.impl.InventoryDBRequest;
import com.young.eshop.inventory.result.Result;
import com.young.eshop.inventory.service.InventoryService;
import com.young.eshop.inventory.service.RequestAsyncProcessService;
import com.young.eshop.inventory.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.controller
 * @ClassName InventoryController
 * @Description 商品库存Controller
 * @Author young
 * @Modify young
 * @Date 2020/2/19 18:03
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    RequestAsyncProcessService requestAsyncProcessService;


    /**
     * @return com.young.eshop.inventory.result.Result
     * @Description 更新库存的数据记录
     * 1. 将更新数据的记录路由到指定的队列中
     * 2. 后台不断的将从队列中取值去处理
     * @param: inventory
     * @Author young
     * @CreatedTime 2020/2/19 22:11
     * @Version V1.0.0
     **/
    @PostMapping(value = "/updateInventory")
    public Result updateInventory(@RequestBody Inventory inventory) {
        InventoryDBRequest inventoryDBRequest = new InventoryDBRequest(inventory, inventoryService);
        requestAsyncProcessService.process(inventoryDBRequest);
        return ResultUtils.success();
    }


    /**
     * @return com.young.eshop.inventory.result.Result
     * @Description 获取库存的数据记录
     * @param: id  库存id
     * @Author young
     * @CreatedTime 2020/2/19 22:11
     * @Version V1.0.0
     **/
    @GetMapping(value = "/getInventory/{id}")
    public Result getInventory(@PathVariable("id") Integer id) {
        try {

            Request request = new InventoryCacheRequest(id, inventoryService);
            requestAsyncProcessService.process(request);
            // 将请求扔给service异步去处理以后，就需要while(true)一会儿，在这里hang住
            // 去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;
            // 等待超过200ms没有从缓存中获取到结果
            while (true) {
                if (waitTime > 200) {
                    break;
                }
                // 尝试去redis中读取一次商品库存的缓存数据
                Inventory inventoryCache = inventoryService.getInventoryCache(id);

                // 如果读取到了结果，那么就返回
                if (inventoryCache != null) {
                    return ResultUtils.success(inventoryCache);
                } else {
                    // 如果没有读取到结果，那么等待一段时间
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }
            // 直接尝试从数据库中读取数据
            Inventory inventory = inventoryService.findInventory(id);
            if (Objects.nonNull(inventory)) {
                return ResultUtils.success(inventory);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ResultUtils.success();
    }
}
