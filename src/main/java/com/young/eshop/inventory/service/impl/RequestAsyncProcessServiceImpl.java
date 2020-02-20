package com.young.eshop.inventory.service.impl;

import com.young.eshop.inventory.request.Request;
import com.young.eshop.inventory.request.RequestQueue;
import com.young.eshop.inventory.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.service.impl
 * @ClassName RequestAsyncProcessServiceImpl
 * @Description 请求异步处理的service实现
 * @Author young
 * @Modify young
 * @Date 2020/2/19 17:08
 * @Version 1.0.0
 **/
@Service
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

    /**
     * @return void
     * @Description 更新库存的数据记录
     * 1. 将更新数据的记录路由到指定的队列中
     * 2. 后台不断的将从队列中取值去处理
     * @param: request
     * @Author young
     * @CreatedTime 2020/2/19 18:15
     * @Version V1.0.0
     **/
    @Override
    public void process(Request request) {
        try {
            // 做请求的路由，根据每个请求的商品id，路由到对应的内存队列中去
            ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getInventoryId());
            // 将请求放入对应的队列中，完成路由操作
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取商品库存路由到的内存队列
     *
     * @param productId 商品id
     * @return java.util.concurrent.ArrayBlockingQueue 内存队列
     */
    private ArrayBlockingQueue<Request> getRoutingQueue(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        // 先获取productId的hash值
        String key = String.valueOf(productId);
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

        // 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
        // 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
        // 所以任何一个商品id都会被固定路由到同样的一个内存队列中去的
        int index = (requestQueue.queueSize() - 1) & hash;
        return requestQueue.getQueue(index);
    }
}
