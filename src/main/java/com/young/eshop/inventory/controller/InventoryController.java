package com.young.eshop.inventory.controller;

import com.young.eshop.inventory.model.Inventory;
import com.young.eshop.inventory.request.Request;
import com.young.eshop.inventory.request.impl.InventoryCacheRequest;
import com.young.eshop.inventory.request.impl.InventoryDBRequest;
import com.young.eshop.inventory.result.Result;
import com.young.eshop.inventory.service.InventoryService;
import com.young.eshop.inventory.service.RequestAsyncProcessService;
import com.young.eshop.inventory.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@Slf4j
@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Resource
    InventoryService inventoryService;

    @Resource
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
    public Result<Inventory> updateInventory(@RequestBody Inventory inventory) {
        Integer id = inventory.getId();
        Integer count = inventory.getInventoryCount();
        log.info("===========双写日志===========: 接收到更新商品库存的请求，商品id:{},商品库存数量: {}", id, count);
        Request inventoryDBRequest = new InventoryDBRequest(inventory, this.inventoryService);
        requestAsyncProcessService.route(inventoryDBRequest);
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
    public Result<Inventory> getInventory(@PathVariable("id") Integer id) {
        log.info("===========双写日志===========: 接收到获取商品库存的请求，商品库存id: {}", id);

        Inventory inventory;

        try {

            Request request = new InventoryCacheRequest(id, inventoryService);
            requestAsyncProcessService.route(request);

            // 将请求扔给service异步去处理以后，就需要while(true)一会儿，在这里hang住
            // 去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
            long startTime = System.currentTimeMillis();
            long endTime;
            long waitTime = 0L;
            // 等待超过200ms没有从缓存中获取到结果
            while (waitTime <= 200) {
                // 一般公司里面，面向用户的读请求控制在200ms就可以了 1秒(s)=1000毫秒(ms)
                // 尝试去redis中读取一次商品库存的缓存数据
                inventory = inventoryService.getInventoryCache(id);

                // 如果读取到了结果，那么就返回
                if (Objects.nonNull(inventory)) {
                    return ResultUtils.success(inventory);
                } else {
                    // 如果没有读取到结果，那么等待一段时间
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }

            // 等待超过200ms没有从缓存中获取到结果
            // 直接尝试从数据库中读取数据
            inventory = inventoryService.findInventory(id);
            if (Objects.nonNull(inventory)) {
                //将缓存刷新一下
                // 这个过程，实际上是一个读操作的过程，但是没有放在队列中串行去处理，还是有数据不一致的问题
                // 代码会运行到这里，只有三种情况：
                // 1、就是说，上一次也是读请求，数据刷入了redis，但是redis LRU算法给清理掉了，标志位还是false
                // 所以此时下一个读请求是从缓存中拿不到数据的，再放一个读Request进队列，让数据去刷新一下
                // 2、可能在200ms内，就是读请求在队列中一直积压着，没有等待到它执行（在实际生产环境中，基本是比较坑了）
                // 所以就直接查一次库，然后给队列里塞进去一个刷新缓存的请求
                // 3、数据库里本身就没有，缓存穿透，穿透redis，请求到达mysql库
                inventoryService.setInventoryCache(inventory);
                return ResultUtils.success(inventory);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ResultUtils.success();
    }
}
