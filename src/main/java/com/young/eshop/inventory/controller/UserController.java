package com.young.eshop.inventory.controller;

import com.young.eshop.inventory.model.User;
import com.young.eshop.inventory.result.Result;
import com.young.eshop.inventory.result.ResultEnum;
import com.young.eshop.inventory.service.UserService;
import com.young.eshop.inventory.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户Controller控制器
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/getUser")
    public User getUser() {
        return userService.findUserInfo();
    }

    @GetMapping("/getUserInfo")
    public Result<User> getUserInfo() {
//        int a = 1 / 0; //handle会统一处理异常
//        return ResultUtils.success(userService.findUserInfo());
        return ResultUtils.error(ResultEnum.NET_FAIL.ordinal(), ResultEnum.NET_FAIL.getMsg());
    }

    @PostMapping("/insert")
    public Result<Object> insert(@RequestBody User user) {
        userService.insert(user);
        return ResultUtils.success();
    }

    //   @GetMapping("/getCachedUserInfo")
    //   public User getCachedUserInfo() {
    //       return userService.getCachedUserInfo();
//    }

    //  @PostMapping("/setCachedUserInfo")
    //   public void setCachedUserInfo(@RequestBody User user) {
    //      userService.setCachedUserInfo(user);
    // }

}
