package com.young.eshop.inventory.advice;

import com.young.eshop.inventory.exception.BusinessException;
import com.young.eshop.inventory.result.Result;
import com.young.eshop.inventory.result.ResultEnum;
import com.young.eshop.inventory.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.advice
 * @ClassName ExceptionHandler
 * @Description ControllerAdvice注解只拦截Controller不会拦截Interceptor的异常。
 * 如果有需要，可以用Logger记录异常信息
 * @Author young
 * @Modify young
 * @Date 2020/2/19 14:15
 * @Version 1.0.0
 **/
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return com.young.eshop.inventory.result.Result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        Result result = ResultUtils.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        return result;
    }

    /**
     * 处理所有业务异常
     *
     * @param businessException
     * @return com.young.eshop.inventory.result.Result
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result handleBusinessException(BusinessException businessException) {
        return ResultUtils.error(businessException.getCode(), businessException.getMessage());
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResultUtils.error(ResultEnum.VALID_ERROR.getCode(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

    }
}
