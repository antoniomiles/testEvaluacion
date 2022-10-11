package com.pichincha.automationtest.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesReader {
    private Properties proper = new Properties();
    Logger logger = Logger.getLogger(this.getClass().getName());

    public String getPropiedad(String atributopro) {
        InputStream archivoProp = null;
        try {
            archivoProp = PropertiesReader.class.getClassLoader()
                    .getResourceAsStream("properties/manualtest.properties");
            this.proper.load(archivoProp);
        } catch (IOException e) {
            logger.log(Level.WARNING, "ERROR: ", e);
        } finally {
            if (archivoProp != null) {
                try {
                    archivoProp.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "ERROR: ", e);
                }
            }
        }
        return this.proper.getProperty(atributopro);
    }
}
