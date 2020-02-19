package com.young.eshop.inventory.service;

import com.young.eshop.inventory.request.Request;

/**
 * 请求异步执行的service
 *
 * @author Administrator
 */
public interface RequestAsyncProcessService {

    /**
     * 更新库存的数据记录
     * 1. 将更新数据的记录路由到指定的队列中
     * 2. 后台不断的将从队列中取值去处理
     *
     * @param request
     */
    void process(Request request);
}
