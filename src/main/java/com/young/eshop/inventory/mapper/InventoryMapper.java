package com.young.eshop.inventory.mapper;

import com.young.eshop.inventory.model.Inventory;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inventory record);

    int insertSelective(Inventory record);

    Inventory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inventory record);

    int updateByPrimaryKey(Inventory record);
}