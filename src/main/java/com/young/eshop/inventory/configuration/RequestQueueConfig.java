package com.young.eshop.inventory.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.configuration
 * @ClassName RequestQueueConfig
 * @Description
 * @Author young
 * @Modify young
 * @Date 2020/2/18 11:55
 * @Version 1.0.0
 **/
@Data
@Configuration
@PropertySource("classpath:queue.properties")
public class RequestQueueConfig {

    /*
     * 1.@Value不能静态成员上使用；
     *2.使用@Value读取属性值的类对象需要交给spring容器管理。
     */

    /**
     * 核心线程数
     */
    @Value("${request.queue.corePoolSize}")
    private Integer corePoolSize;

    /**
     * 线程池最大线程数
     */
    @Value("${request.queue.maximumPoolSize}")
    private Integer maximumPoolSize;

    /**
     * 线程最大存活时间
     */
    @Value("${request.queue.keepAliveTime}")
    private Long keepAliveTime;

    /**
     * 时间单位
     */
    @Value("${request.queue.timeUnit}")
    private String timeUnit;

}
