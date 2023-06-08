package com.pichincha.automationtest.util;

import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;

public class EnvironmentConfig {

    private static final EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();

    public String getVariable(String variable) {
        String value = System.getenv(variable);//obtiene desde variables de entorno
        if (value == null || value.isEmpty()) {
            value = System.getProperty(variable);//obtiene desde variables del sistema
            if (value == null || value.isEmpty()) {
                value = environmentVariables.getProperty(variable);//propio de serenity obtiene variables: entorno, sistema y properties serenity
            }
        }
        return value == null ? "" : value;
    }

}