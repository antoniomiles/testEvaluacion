package com.pichincha.automationtest.util;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.io.File;

public class PathConstants {
    private PathConstants() {
    }

    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

    private static final String FEATURE_PATH = System.getProperty("user.dir")
            + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator
            + "features" + File.separator;

    private static final String DATA_PATH = System.getProperty("user.dir")
            + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator
            + "data" + File.separator;

    /**
     * Evalua si la ruta de los archivos features se encuentra parametrizada en el
     * archivo serenity.properties
     * De lo contrario, tomara el valor por defecto de la variable
     * FEATURE_PATH: "src/test/resources/features/"
     * 
     * @return El valor de la ruta contenedora de los features
     */
    public static String featurePath() {
        if (variables.getProperty("features.path") != null)
            return variables.getProperty("features.path");
        return FEATURE_PATH;
    }

    /**
     * Evalua si la ruta de los archivos de data (.csv) se encuentra parametrizada
     * en la propiedad "data.path" en el archivo serenity.properties
     * De lo contrario, tomara el valor por defecto de la variable
     * DATA_PATH: "src/test/resources/data/"
     * 
     * @return El valor de la ruta contenedora de los features
     */
    public static String dataPath() {
        if (variables.getProperty("data.path") != null)
            return variables.getProperty("data.path");
        return DATA_PATH;
    }

    public static String validatePath(String path) {
        String[] separators = { "/", "\\", "-" };

        for (String separator : separators) {
            if (path.contains(separator)) {
                path = path.replace(separator, File.separator);
                break;
            }
        }
        return path;
    }
}
