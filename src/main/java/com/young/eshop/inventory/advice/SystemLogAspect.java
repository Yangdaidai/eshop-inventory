package com.young.eshop.inventory.advice;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Title: SystemLogAspect
 * Description: aop日志
 * Copyright: Copyright (c) young 2018
 *
 * @author young
 * @version V1.0.0
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {
    private final static String pointCut = "(execution(public * com.young.eshop.inventory.controller.*.*.*(..)))"
//            + "&& !execution(public *  com.young.eshop.inventory.controller.UserController.*(..)))"
            ;

    private ConcurrentHashMap<String, Long> timeMap = new ConcurrentHashMap<>(16);

    public SystemLogAspect() {
        log.info(">>>>>>>>>>>>>>>>>>>AOP日志初始化<<<<<<<<<<<<<<<<<<<<<");
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @Before(pointCut)
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = this.getRequest();
        String args = Arrays.toString(joinPoint.getArgs());
        log.info("\n请求开始>>>>>\n" +
                        "请求的链接={},\n" +
                        "请求的接口={}，\n" +
                        "请求人ip:{},\n" +
                        "参数={},\n" +
                        "sql:",
                request.getRequestURL(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                this.getIpAddress(),
                args);

        timeMap.put("startTime", System.currentTimeMillis());
    }

    @After(pointCut)
    public void doAfter() {
    }

    @AfterReturning(returning = "result", pointcut = pointCut)
    public void doAfterReturning(Object result) {
        String rStr = result == null ? "null" : JSON.toJSONString(result);
        log.info("\n返回结果={}" +
                        "\n耗时={}" +
                        "\n<<<<<请求结束",
                rStr,
                System.currentTimeMillis() - timeMap.get("startTime")
        );
    }

    @AfterThrowing(value = pointCut, throwing = "e")
    public void afterThrowing(Throwable e) {
        log.error("异常全限定名{},异常message:{},"
                        + "\n耗时{}"
                        + "\n<<<<<请求结束",
                e.getClass().getName(), e.getMessage(),
                System.currentTimeMillis() - timeMap.get("startTime"));
    }

    public String getIpAddress() {
        HttpServletRequest request = this.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
