package com.pichincha.automationtest.util;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;

public class EnvironmentConfig {
    private EnvironmentVariables variables;

    public String getValue(String property) {
        return EnvironmentSpecificConfiguration.from(variables).getProperty(property);
    }
}