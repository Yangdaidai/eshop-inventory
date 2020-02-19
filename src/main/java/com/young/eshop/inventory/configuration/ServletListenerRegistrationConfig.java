package com.young.eshop.inventory.configuration;

import com.young.eshop.inventory.listener.InitListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EventListener;

/**
 * @ClassName ServletListenerRegistrationConfig
 * @Description 在容器启动的时候，注册自定义的Listener
 * 1. 在监听器中初始化线程池
 * @Author young
 * @Date 2020/2/17 13:00
 * @Version 1.0
 **/
@Configuration
public class ServletListenerRegistrationConfig {

    /**
     * @return org.springframework.boot.web.servlet.ServletListenerRegistrationBean
     * @Description 注册自定义的Bean 并且设置监听器，该监听器初始化线程池
     * @param:
     * @Author young
     * @CreatedTime 2020/2/17 16:59
     * @Version V1.0.0
     **/
    @Bean
    public ServletListenerRegistrationBean<EventListener> registrationBean() {
        ServletListenerRegistrationBean<EventListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new InitListener());
        return servletListenerRegistrationBean;
    }
}
