package com.pichincha.automationtest.util;



import com.pichincha.automationtest.util.dbconection.enums.DataBaseType;
import org.stringtemplate.v4.ST;
import java.util.*;

public class ConfigurationParamUtils {

    private ConfigurationParamUtils(){}

    private static final  String[] dbConnParameters={"DB_USERNAME", "DB_PASSWORD", "DB_HOST", "DB_PORT", "DB_TYPE", "DB_NAME"};

    public static Map<String, Object> loadEnviromentalValues() {
        Map<String, String> valuesMap;
        Optional<Map<String,String>> opValuesEnvMap = createConfigMapEnvParams();
        Map<String,Object> configMap = new HashMap<>();
        DataBaseType dbType;
        String url;
        if(opValuesEnvMap.isPresent()){
            valuesMap = opValuesEnvMap.get();
        }else{
            PropertiesUtils propertiesUtils = new PropertiesUtils();
            Optional<Properties> opProperties = propertiesUtils.getPropValues();
            valuesMap = opProperties.map(ConfigurationParamUtils::createConfigMapPropertiesParams)
                    .orElseGet(HashMap::new);
        }

        if(checkNulls(valuesMap)){
            throw new IllegalArgumentException("Valores nulos para la creación de la conexion a la base");
        }else {
            dbType = DataBaseType.valueOf(valuesMap.get("DB_TYPE"));
            url = createURL(dbType.getJdbcUrl(), valuesMap.get("DB_HOST"),
                    valuesMap.get("DB_PORT"), valuesMap.get("DB_NAME"));
        }
        configMap.put("username",valuesMap.get("DB_USERNAME"));
        configMap.put("password",valuesMap.get("DB_PASSWORD"));
        configMap.put("url",url);
        configMap.put("driverClassName",dbType.getDriver());
        return configMap;
    }

    private static boolean checkNulls(Map<String, String> valuesMap) {
        if(valuesMap.isEmpty()) {
            return true;
        }else{
            for (String parameter : valuesMap.values()) {
                if (Objects.isNull(parameter)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Optional<Map<String,String>> createConfigMapEnvParams() {
        Map<String,String> valuesMap = new HashMap<>();
        for(String parameter:dbConnParameters){
            if(Objects.nonNull(System.getenv(parameter))) {
                valuesMap.put(parameter, System.getenv(parameter));
            }else{
                return Optional.empty();
            }
        }
        return Optional.of(valuesMap);
    }
    private static Map<String,String> createConfigMapPropertiesParams(Properties properties) {
        Map<String,String> valuesMap = new HashMap<>();
        for(String parameter:dbConnParameters){
            String value = properties.getProperty(parameter);
            valuesMap.put(parameter, value);
        }
        return valuesMap;
    }

    private static String createURL(String urlTemplate, String host, String port, String dbName) {
        ST stUrl = new ST(urlTemplate);
        stUrl.add("host",host);
        stUrl.add("port", port);
        stUrl.add("database", dbName);
        return stUrl.render();
    }
}
