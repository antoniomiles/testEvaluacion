package com.pichincha.automationtest.util;

public class EnvProperties {
    private EnvProperties() {
    }

    public static final String ACTOR = "actor";

    public static String getVariable(String variable) {
        String value = System.getenv(variable);
        if (value == null || value.isEmpty()) {
            value = System.getProperty(variable);
        }
        return value == null ? "" : value;
    }
}
