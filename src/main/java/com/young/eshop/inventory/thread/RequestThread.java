package com.young.eshop.inventory.thread;

import com.young.eshop.inventory.request.Request;
import com.young.eshop.inventory.request.RequestQueue;
import com.young.eshop.inventory.request.impl.InventoryCacheRequest;
import com.young.eshop.inventory.request.impl.InventoryDBRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.thread
 * @ClassName RequestThread
 * @Description 执行请求的工作线程
 * 线程和队列进行绑定，然后再线程中处理对应的业务逻辑
 * @Author young
 * @Modify young
 * @Date 2020/2/17 18:26
 * @Version 1.0.0
 **/
@Slf4j
public class RequestThread implements Callable<Boolean> {

    /**
     * 自己监控的队列
     */
    private ArrayBlockingQueue<Request> queue;

    public RequestThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() {
        try {
            while (true) {
                // Blocking就是说明，如果队列满了，或者是空的，那么都会在执行操作的时候，阻塞住
                Request request = queue.take();
                boolean forceFresh = request.isForceFresh();

                // 如果需要强制更新的话
                if (!forceFresh) {
                    RequestQueue requestQueue = RequestQueue.getInstance();
                    //标志位
                    Map<String, Boolean> flagMap = requestQueue.getFlagMap();

                    /**
                     * 更新（写）请求去重优化
                     */

                    //如果是更新数据库的请求,那么就将那个inventoryId对应的标识设置为true
                    //更新数据库--->flag=true
                    if (request instanceof InventoryDBRequest) {
                        flagMap.put(String.valueOf(request.getInventoryId()), Boolean.TRUE);

                    } else if (request instanceof InventoryCacheRequest) {
                        Boolean flag = flagMap.get(String.valueOf(request.getInventoryId()));

                        //空数据读请求过滤优化
                        // 如果flag为空 则说明读取缓存的操作
                        //但是如果flag压根儿就没有呢，就说明这个数据，无论是写请求，还是读请求，都没有过
                        //那这个时候过来的读请求，发现flag是null，就可以认为数据库里肯定也是空的，那就不会去读取了
                        //或者说，我们也可以认为每个商品有一个最最初始的库存，但是因为最初始的库存肯定会同步到缓存中去的，有一种特殊的情况，就是说，商品库存本来在redis中是有缓存的
                        //但是因为redis内存满了，就给干掉了，但是此时数据库中是有值得
                        //那么在这种情况下，可能就是之前没有任何的写请求和读请求的flag的值，此时还是需要从数据库中重新加载一次数据到缓存中的
                        if (null == flag) {
                            flagMap.put(String.valueOf(request.getInventoryId()), Boolean.FALSE);
                        }

                        // 更新缓存的请求
                        // 如果标识不为空,并且是true,说明上一个请求是更新数据库的
                        // 那么此时我们需要将标志位修改为false
                        //更新缓存--->flag=false
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
                            return Boolean.TRUE;
                        }
                    }
                }

                // 执行这个request操作
                request.process();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }
}
