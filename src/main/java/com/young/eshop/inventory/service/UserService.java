package com.young.eshop.inventory.service;

import com.young.eshop.inventory.model.User;

/**
 * 用户Service接口
 *
 * @author Administrator
 */
public interface UserService {

    /**
     * 查询用户信息
     *
     * @return 用户信息
     */
    User findUserInfo();

    /**
     * 查询redis中缓存的用户信息
     *
     * @return user
     */
    User getCachedUserInfo();

    void setCachedUserInfo(User user);
}
