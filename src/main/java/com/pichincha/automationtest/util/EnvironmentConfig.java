package com.pichincha.automationtest.util;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;

public class EnvironmentConfig {
    private EnvironmentVariables variables;

    public String getValue(String property) {
        return EnvironmentSpecificConfiguration.from(variables).getProperty(property);
    }

    public String getVariable(String variable) {
        String value = System.getenv(variable);
        if (value == null || value.isEmpty()) {
            value = System.getProperty(variable);
        }
        return value == null ? "" : value;
    }
}