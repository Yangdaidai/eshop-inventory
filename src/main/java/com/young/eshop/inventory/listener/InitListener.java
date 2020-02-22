package com.young.eshop.inventory.listener;

import com.young.eshop.inventory.thread.RequestThreadPool;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.listener
 * @ClassName InitListener
 * @Description 系统初始化监听器
 * @Author young
 * @Modify young
 * @Date 2020/2/17 17:16
 * @Version 1.0.0
 **/
@Slf4j
public class InitListener implements ServletContextListener {

    /**
     * 初始化工作线程池和内存队列
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //初始化工作线程池和内存队列
        RequestThreadPool.init();
        log.info("初始化工作线程池和内存队列");
    }

    /**
     * 监听器销毁执行的逻辑
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
