package com.young.eshop.inventory.mapper;

import com.young.eshop.inventory.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User findUserInfo();

}
