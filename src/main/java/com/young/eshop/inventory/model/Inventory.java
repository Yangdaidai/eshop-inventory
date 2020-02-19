package com.young.eshop.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * inventory
 *
 * @author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory implements Serializable {
    /**
     * 库存主键
     */
    private Integer id;

    /**
     * 库存名称
     */
    private String name;

    /**
     * 库存数量
     */
    private Integer inventoryCount;

    private static final long serialVersionUID = 1L;
}