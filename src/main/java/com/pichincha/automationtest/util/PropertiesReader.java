package com.pichincha.automationtest.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private Properties proper = new Properties();

    public String getPropiedad(String atributopro) {
        InputStream archivoProp = null;
        try {
            archivoProp = PropertiesReader.class.getClassLoader().getResourceAsStream("properties/manualtest.properties");
            this.proper.load(archivoProp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (archivoProp != null) {
                try {
                    archivoProp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.proper.getProperty(atributopro);
    }
}
