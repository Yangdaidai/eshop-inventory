package test;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package test
 * @ClassName PropertiesLoadTest
 * @Description
 * @Author young
 * @Modify young
 * @Date 2020/2/22 23:58
 * @Version 1.0.0
 **/
@Disabled
public class PropertiesLoadTest {
    @Test
    @DisplayName("test Properties Load")
    public void testPropertiesLoad() {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("queue.properties");
            String corePoolSize = properties.getProperty("request.queue.corePoolSize");
            String maximumPoolSize = properties.getProperty("request.queue.maximumPoolSize");
            System.out.println("corePoolSize = " + Integer.valueOf(corePoolSize));
            System.out.println("maximumPoolSize = " + Integer.valueOf(maximumPoolSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
