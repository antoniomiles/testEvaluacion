package com.pichincha.automationtest.util;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ConfigurationParamUtilsTest {

    @Test
    public void loadEnviromentalValues() {
        Map<String, Object> configMap = ConfigurationParamUtils.loadEnviromentalValues("MYSQL");
        assertNotNull(configMap);
        assertEquals("vvalencia", configMap.get("username"));
    }

    @Test
    public void loadEnviromentalValuesSQL() {
        Map<String, Object> configMap = ConfigurationParamUtils.loadEnviromentalValues("SQLSERVER");
        assertNotNull(configMap);
        assertEquals("vvalencia", configMap.get("username"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void loadEnviromentalValuesPOST() {
        ConfigurationParamUtils.loadEnviromentalValues("POSTGRES");

    }
}