package com.pichincha.automationtest.util;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ConfigurationParamUtilsTest {

    @Test
    public void loadEnviromentalValues() {
        Map<String, Object> configMap = ConfigurationParamUtils.loadEnviromentalValues();
        assertNotNull(configMap);
        assertEquals("vvalencia", configMap.get("username"));
    }
}