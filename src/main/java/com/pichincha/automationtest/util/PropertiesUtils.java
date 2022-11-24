package com.pichincha.automationtest.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

public class PropertiesUtils {
    public Optional<Properties> getPropValues() {
        Properties properties = new Properties();
        String projectDirectory = FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath().toString();
        String propFileName = projectDirectory +"/serenity.properties";

        try(InputStream inputStream =Files.newInputStream(Paths.get(propFileName))){
            properties.load(inputStream);
            return Optional.of(properties);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
