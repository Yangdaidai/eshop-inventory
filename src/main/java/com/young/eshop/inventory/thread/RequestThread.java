package com.young.eshop.inventory.thread;

import com.young.eshop.inventory.request.Request;
import lombok.extern.slf4j.Slf4j;

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
                // 执行这个request操作
                request.process();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }
}
