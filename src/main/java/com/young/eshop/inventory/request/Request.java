package com.young.eshop.inventory.request;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.request
 * @ClassName Request
 * @Description 请求的接口
 * 该接口封装了请求的方法，实现类来实现具体的业务逻辑
 * @Author young
 * @Modify young
 * @Date 2020/2/17 18:02
 * @Version 1.0.0
 **/
public interface Request {
    void process();

    Integer getInventoryId();

    boolean isForceFresh();

}
