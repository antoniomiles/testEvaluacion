package com.pichincha.automationtest.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(BlockJUnit4ClassRunner.class)
public class PropertiesUtilsTest {

    @Test
    public void getPropValues(){
        PropertiesUtils propertiesUtils = new PropertiesUtils();
        Optional<Properties> opProperties = propertiesUtils.getPropValues();
        Properties properties ;
        if(opProperties.isPresent()) {
            properties = opProperties.get();
            if(Objects.nonNull(properties.get("mysql.username"))) {
                assertEquals("vvalencia", properties.get("mysql.username"));
            }
        }
    }
}