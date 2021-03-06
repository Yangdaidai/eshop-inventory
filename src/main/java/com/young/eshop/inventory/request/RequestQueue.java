package com.young.eshop.inventory.request;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.request
 * @ClassName RequestQueue
 * @Description 请求队列 这里需要使用单例模式来确保请求的队列的对象只有一个
 * @Author young
 * @Modify young
 * @Date 2020/2/17 18:23
 * @Version 1.0.0
 **/
public class RequestQueue {

    /**
     * 构造器私有化，这样就不能通过new来创建实例对象
     * 这里构造器私有化 这点跟枚举一样的，所以我们也可以通过枚举来实现单例模式
     */
    private RequestQueue() {
    }

    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

    /**
     * 标志位
     * 主要用来判断读请求去重的操作
     * Boolean true 表示更新数据的操作 false 表示读取缓存的操作
     */
    private Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>(1);

    /**
     * 使用静态内部类来实现单例的模式（绝对的线程安全）
     */
    private static class Singleton {

        //私有的静态变量，确保该变量不会被外部调用
        private static RequestQueue requestQueue;

        //静态代码块在类初始化时执行一次

        static {
            requestQueue = new RequestQueue();
        }

        /**
         * @return com.young.eshop.inventory.thread.RequestQueue
         * @Description 静态内部类对外提供实例的获取方法
         * @param:
         * @Author young
         * @CreatedTime 2020/2/17 17:38
         * @Version V1.0.0
         **/
        public static RequestQueue getInstance() {
            return requestQueue;
        }

    }

    /**
     * @return com.young.eshop.inventory.thread.RequestRequeue
     * @Description 请求线程池类对外提供获取实例的方法
     * 由于外部类没有RequestQueue的实例对象，所以除了该方法，
     * 外部类无法创建额外的RequestQueue对象
     * @param:
     * @Author young
     * @CreatedTime 2020/2/17 17:42
     * @Version V1.0.0
     **/
    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 向容器中添加队列
     *
     * @param queue 队列
     */
    public void add(ArrayBlockingQueue<Request> queue) {
        this.queues.add(queue);
    }

    /**
     * 获取内存队列的数量
     *
     * @return int 队列的数量
     */
    public int queueSize() {
        return queues.size();
    }

    /**
     * 获取内存队列
     *
     * @param index 队列索引
     * @return ArrayBlockingQueue
     */
    public ArrayBlockingQueue<Request> getQueue(int index) {
        return queues.get(index);
    }

    /**
     * 返回标志位
     *
     * @return tagMap 返回标志位
     */
    public Map<Integer, Boolean> getFlagMap() {
        return this.flagMap;
    }

}
