package com.young.eshop.inventory.result;

import lombok.Data;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.result
 * @ClassName Result
 * @Description
 * @Author young
 * @Modify young
 * @Date 2020/2/19 13:42
 * @Version 1.0.0
 **/
@Data
public class Result<T> {
    /**
     * 码值.
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 具体的内容.
     */
    private T data;


}
