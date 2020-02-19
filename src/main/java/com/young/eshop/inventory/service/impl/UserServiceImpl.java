package com.young.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.young.eshop.inventory.dao.RedisDao;
import com.young.eshop.inventory.mapper.UserMapper;
import com.young.eshop.inventory.model.User;
import com.young.eshop.inventory.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户Service实现类
 *
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisDao redisDao;

    @Override
    public User findUserInfo() {
        return userMapper.findUserInfo();
    }


    @Override
    public User getCachedUserInfo() {
        redisDao.set("cached_user_lisi", "{\"id\": 1,\"name\": \"lisi\", \"age\":30}");

        String userJSON = (String) redisDao.get("cached_user_lisi");
        JSONObject userJSONObject = JSONObject.parseObject(userJSON);

        User user = new User();
        user.setId(userJSONObject.getInteger("id"));
        user.setName(userJSONObject.getString("name"));
        user.setAge(userJSONObject.getInteger("age"));

        return user;
    }

}
