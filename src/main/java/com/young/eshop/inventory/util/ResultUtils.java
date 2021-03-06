package com.young.eshop.inventory.util;

import com.young.eshop.inventory.result.Result;
import com.young.eshop.inventory.result.ResultEnum;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.util
 * @ClassName ResultUtils
 * @Description 返回结果工具类
 * @Author young
 * @Modify young
 * @Date 2020/2/19 13:48
 * @Version 1.0.0
 **/
public class ResultUtils {
    public static <T> Result<T> success(T object) {

        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("success");
        result.setData(object);
        return result;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error() {
        return error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
    }

    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
