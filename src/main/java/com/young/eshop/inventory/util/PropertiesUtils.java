package com.young.eshop.inventory.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.eshop.inventory.util
 * @ClassName PropertiesLoaderUtils
 * @Description 配置文件加载工具类
 * @Author young
 * @Modify young
 * @Date 2020/2/23 0:49
 * @Version 1.0.0
 **/
public class PropertiesUtils {

    public static Properties getProperties(String resourceName) {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(resourceName);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
