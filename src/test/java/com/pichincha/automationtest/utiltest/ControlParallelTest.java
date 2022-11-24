package com.pichincha.automationtest.utiltest;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class ControlParallelTest {
    private ControlParallelTest() {
    }

    private static String externalDataSt = "RunnerEjecutandose";
    private static String msgError = "ERROR: ";
    private static Logger logger = Logger.getLogger(ControlParallelTest.class.getName());

    public static void setOrRemoveExecution(String addOrDeleteRunner) throws IOException {
        File propertiesFile = new File(
                System.getProperty("user.dir") + "/src/test/resources/properties/parallelcontrol.properties");
        List<String> propertiesModified = addOrDeleteExecutioInProperties(propertiesFile, addOrDeleteRunner);
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get(propertiesFile.getAbsolutePath()), StandardCharsets.UTF_8);
            for (final String writeline : propertiesModified) {
                writer.write(writeline);
                writer.write("\n");
            }
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, msgError, e);
                }
            }
        }
    }

    private static List<String> addOrDeleteExecutioInProperties(final File featureFile, String addOrDelete)
            throws IOException {
        final List<String> fileData = new ArrayList<>();
        BufferedReader buffReader = null;
        try {
            buffReader = Files.newBufferedReader(Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8);
            String data;

            if (addOrDelete.equals("add")) {
                while ((data = buffReader.readLine()) != null) {
                    fileData.add(data);
                }
                fileData.add(externalDataSt);
            } else {
                int cont = 0;
                while ((data = buffReader.readLine()) != null) {
                    if (data.contains(externalDataSt)) {
                        cont++;
                    }
                    if (cont != 1) {
                        fileData.add(data);
                    }
                }
            }
        } finally {
            if (buffReader != null) {
                try {
                    buffReader.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, msgError, e);
                }
            }
        }
        return fileData;
    }

    public static boolean validateExecution() {
        boolean delete = false;
        File featureFile = new File(
                System.getProperty("user.dir") + "/src/test/resources/properties/parallelcontrol.properties");
        BufferedReader buffReader = null;
        try {
            buffReader = Files.newBufferedReader(Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8);
            String data;
            int cont = 0;
            while ((data = buffReader.readLine()) != null) {
                if (data.contains(externalDataSt)) {
                    cont++;
                }
            }
            if (cont == 1) {
                delete = true;
            }

        } catch (IOException e) {
            log.error("error al leer properties parallelcontrol.properties " + "\n" + e.getMessage(), e);
        } finally {
            if (buffReader != null) {
                try {
                    buffReader.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, msgError, e);
                }
            }
        }
        return delete;
    }
}
