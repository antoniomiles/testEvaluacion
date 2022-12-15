package com.pichincha.automationtest.util.dbconection;

import com.pichincha.automationtest.util.ConfigurationParamUtils;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class DataBaseUtilsTest {

    @Test
    public void initialization() {
        Map<String, Object> configMap = ConfigurationParamUtils.loadEnviromentalValues("MONGO");
        assertThrows(UnsupportedOperationException.class, () -> new DataBaseUtils(configMap));
    }

    @Test
    public void notnull() {
        Map<String, Object> configMap = ConfigurationParamUtils.loadEnviromentalValues("MYSQL");
        DataBaseUtils dbUtils = new DataBaseUtils(configMap);
        assertNotNull(dbUtils);
    }
}