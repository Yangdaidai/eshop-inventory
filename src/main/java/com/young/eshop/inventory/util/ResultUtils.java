package com.young.eshop.inventory.util;

import com.young.eshop.inventory.result.Result;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.util
 * @ClassName ResultUitl
 * @Description 返回结果工具类
 * @Author young
 * @Modify young
 * @Date 2020/2/19 13:48
 * @Version 1.0.0
 **/
public class ResultUtils<T> {
    public static <T> Result<T> success(T object) {

        Result result = new Result();
        result.setCode(200);
        result.setMsg("success");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
