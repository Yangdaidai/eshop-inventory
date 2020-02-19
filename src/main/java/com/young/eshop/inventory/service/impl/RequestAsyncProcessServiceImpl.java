package com.young.eshop.inventory.service.impl;

import com.young.eshop.inventory.request.Request;
import com.young.eshop.inventory.request.RequestQueue;
import com.young.eshop.inventory.request.impl.InventoryCacheRequest;
import com.young.eshop.inventory.request.impl.InventoryDBRequest;
import com.young.eshop.inventory.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.Map;
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

            RequestQueue requestQueue = RequestQueue.getInstance();
            Map<String, Boolean> flagMap = requestQueue.getFlagMap();


            /**
             * 更新（写）请求去重优化
             */

            //如果是更新数据库的请求,那么就将那个inventoryId对应的标识设置为true
            //更新数据库--->flag=true
            if (request instanceof InventoryDBRequest) {
                flagMap.put(String.valueOf(request.getInventoryId()), Boolean.TRUE);

            } else if (request instanceof InventoryCacheRequest) {
                // 更新缓存的请求
                // 如果标识不为空,并且是true,说明上一个请求是更新数据库的
                // 那么此时我们需要将标志位修改为false
                //更新缓存--->flag=false
                Boolean flag = flagMap.get(String.valueOf(request.getInventoryId()));
                if (flag != null && flag) {
                    flagMap.put(String.valueOf(request.getInventoryId()), Boolean.FALSE);
                }

                /**
                 * 读请求去重优化
                 * 如果一个读请求过来，发现前面已经有一个写请求和一个读请求了，那么这个读请求就不需要压入队列中了
                 * 因为那个写请求肯定会更新数据库，然后那个读请求肯定会从数据库中读取最新数据，然后刷新到缓存中，
                 * 自己只要hang一会儿就可以从缓存中读到数据了
                 */
                // flag不为空，并且为false时，说明前面已经有数据库+缓存的请求了，
                // 那么这个请求应该是读请求，可以直接过滤掉了，不要添加到内存队列中
                if (flag != null && !flag) {
                    return;
                }
            }

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
