package com.young.eshop.inventory.exception;

import com.young.eshop.inventory.result.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory
 * @ClassName BusinessException
 * @Description 业务异常
 * spring中，只有继承RuntimeException才会进行事务回滚，Exception不会进行事务回滚
 * @Author young
 * @Modify young
 * @Date 2020/2/19 14:00
 * @Version 1.0.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {
    private Integer code;

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

}
