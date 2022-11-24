package com.pichincha.automationtest.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

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
        Properties properties;
        if(opProperties.isPresent()) {
            properties = opProperties.get();
            assertNotNull(opProperties.get());
            assertEquals("vvalencia",properties.get("DB_USERNAME"));
        }

    }
}