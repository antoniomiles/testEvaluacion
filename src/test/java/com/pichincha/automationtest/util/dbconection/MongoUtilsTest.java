package com.pichincha.automationtest.util.dbconection;

import com.pichincha.automationtest.util.ConfigurationParamUtils;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MongoUtilsTest {

    @Test
    public void getMongoUrlString() {
        Map<String, Object> configMap = ConfigurationParamUtils.loadEnviromentalValues("MONGO");
        MongoUtils mongoUtils = new MongoUtils(configMap);
        String mongoUrl = mongoUtils.getMongoUrlString();
        assertEquals("mongodb://vvalencia:Rainbowdark01%24%21@195.88.57.144:27017/?authMechanism=SCRAM-SHA-256&tls=false&authSource=baseprueba"
                ,mongoUrl);
    }
}