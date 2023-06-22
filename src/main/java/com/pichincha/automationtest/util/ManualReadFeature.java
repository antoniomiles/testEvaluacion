package com.pichincha.automationtest.util;

import com.pichincha.automationtest.exceptions.ExcRuntime;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

@Slf4j
public class ManualReadFeature {
    private ManualReadFeature() {
    }

    static PropertiesReader readProperties = new PropertiesReader();

    public static String setPassedOrFailedFromPane(String featureName, String nameScenario, int numScenario) {
        String statusExecution;
        String[] options = {"   No   ", "   Si   "};
        String numInitArrayReadCsv = readProperties.getPropiedad("num.init.array.read.csv");
        if (numInitArrayReadCsv.equals("1")) {
            numScenario = numScenario + 1;
        }

        JOptionPane jOptionPane = new JOptionPane("El  \"" + nameScenario.trim() + "\"  se ejecutó correctamente?",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION,
                null, options, options[0]);
        JDialog jDialog = jOptionPane.createDialog(null, featureName + "   -->   Scenario N° " + numScenario);
        jDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jDialog.setVisible(true);
        String optionSelected = (String) jOptionPane.getValue();
        if (optionSelected.trim().equals("No")) {
            statusExecution = "  @manual-result:failed";
        } else {
            statusExecution = "  @manual-result:passed";
        }
        return statusExecution;
    }

    public static String setPassedOrFailedFromCSV(int numScenario, String filePath) {
        String lineData;
        String statusExecution = "  #EstadoScenarioNoDefinido";
        String numInitArrayReadCsv = readProperties.getPropiedad("num.init.array.read.csv");
        if (numInitArrayReadCsv.equals("1")) {
            numScenario = numScenario + 1;
        }
        try (BufferedReader bfReader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(Paths.get(filePath)), StandardCharsets.UTF_8))) {

            while ((lineData = bfReader.readLine()) != null) {
                String[] numberAndResultTest = lineData.split(",");

                String columnOne = numberAndResultTest[0];
                if (columnOne.trim().equalsIgnoreCase(String.valueOf(numScenario))) {
                    switch (numberAndResultTest[1].trim().toLowerCase()) {
                        case "failed":
                            statusExecution = "  @manual-result:failed";
                            break;
                        case "passed":
                            statusExecution = "  @manual-result:passed";
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            log.error("ERROR en lectura de archivo: " + e.getMessage(), e);
        }
        return statusExecution;
    }

    public static String readManualFeaturePassedOrdFailed(final File featureFile, final int lineScenario) throws IOException {
        String resultScenario = "";
        BufferedReader buffReaderScenario = null;
        try {
            buffReaderScenario = Files.newBufferedReader(Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8);
            String lineOfFeatureFile;
            int countLine = 1;
            while ((lineOfFeatureFile = buffReaderScenario.readLine()) != null) {
                if ((lineOfFeatureFile.trim().contains("@manual-result:") || lineOfFeatureFile.trim().contains("#EstadoScenarioNoDefinido"))
                        && countLine == lineScenario - 1) {
                    resultScenario = lineOfFeatureFile;
                }
                countLine++;
            }
        } finally {
            if (buffReaderScenario != null) {
                try {
                    buffReaderScenario.close();
                } catch (IOException e) {
                    log.error("Error al cerrar buffReaderScenario" + e.getMessage(), e);
                }
            }
        }
        return resultScenario;
    }

    public static void validateScenario(Scenario scenario) {
        String nameFile = "isManualTests";
        File file = new File(System.getProperty("user.dir") + File.separator + "target" + File.separator + "site" + File.separator + "serenity" + File.separator + nameFile);
        try {
            boolean fileCreated = file.createNewFile();
            if (fileCreated) log.info("=====> Archivo " + nameFile + "creado exitosamente");
        } catch (IOException e) {
            log.info("Error al crear archivo " + nameFile + e.getMessage());
        }

        try {
            File featureFile = new File(scenario.getUri());
            int lineScenario = scenario.getLine();
            log.info("Validando scenario de la linea " + lineScenario + " en el feature " + featureFile.getName());
            ManualReadFeature.validatePassedOrdFailed(featureFile, lineScenario);
        } catch (IOException e) {
            throw new ExcRuntime("Error al validar resultado de scenario Manual - " + e.getMessage(), e);
        }
    }

    public static void validatePassedOrdFailed(File featureFile, int lineScenario) throws IOException {
        String passedOrdFailed = readManualFeaturePassedOrdFailed(featureFile, lineScenario);
        String status;
        if (passedOrdFailed.contains("passed")) {
            status = "PASSED";
        } else if (passedOrdFailed.contains("failed")) {
            status = "FAILED";
        } else {
            throw new io.cucumber.java.PendingException("No se encontró el resultado del scenario manual, verificar data/path de resultados");
        }
        assertEquals("ESTADO SCENARIO MANUAL: ", "PASSED", status);
    }
}