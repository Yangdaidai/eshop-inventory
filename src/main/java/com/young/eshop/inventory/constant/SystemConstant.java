package com.young.eshop.inventory.constant;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory
 * @ClassName SystemConstant
 * @Description 系统常量
 * @Author young
 * @Modify young
 * @Date 2020/2/23 1:04
 * @Version 1.0.0
 **/
public class SystemConstant {
    //queue 相关配置
    public static final String QUEUE_PROPERTIES = "queue.properties";

    //核心线程数
    public static final String REQUEST_QUEUE_CORE_POOL_SIZE = "request.queue.corePoolSize";

    //线程池最大线程数
    public static final String REQUEST_QUEUE_MAXIMUM_POOL_SIZE = "request.queue.maximumPoolSize";

    //线程最大存活时间
    public static final String REQUEST_QUEUE_KEEP_ALIVE_TIME = "request.queue.keepAliveTime";

    // 时间单位
    public static final String REQUEST_QUEUE_TIME_UNIT = "request.queue.timeUnit";
}
