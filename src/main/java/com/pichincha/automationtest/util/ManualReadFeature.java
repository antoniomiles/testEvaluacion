package com.pichincha.automationtest.util;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ManualReadFeature {
    private ManualReadFeature() {
    }

    static PropertiesReader readProperties = new PropertiesReader();

    public static String setPassedOrFailedFromPane(String nameScenario, int numScenario) {
        String statusExecution;
        String[] options = { "   No   ", "   Si   " };
        String numInitArrayReadCsv = readProperties.getPropiedad("num.init.array.read.csv");
        if (numInitArrayReadCsv.equals("1")) {
            numScenario = numScenario + 1;
        }

        JOptionPane jOptionPane = new JOptionPane("El  \"" + nameScenario.trim() + "\"  se ejecutó correctamente?",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION,
                null, options, options[0]);
        JDialog jDialog = jOptionPane.createDialog(null, "Scenario N° " + numScenario);
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

    public static String setPassedOrFailedFromCSV(int numScenario, String filePath) throws IOException {
        String lineData = "";
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
                if (columnOne.equalsIgnoreCase(String.valueOf(numScenario))) {
                    switch (numberAndResultTest[1].toLowerCase()) {
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
        }
        return statusExecution;
    }

    public static List<String> readManualFeaturePassedOrdFailed(final File featureFile) throws IOException {
        final List<String> scenarios = new ArrayList<String>();
        try (BufferedReader buffReaderScenario = Files.newBufferedReader(Paths.get(featureFile.getAbsolutePath()),
                StandardCharsets.UTF_8)) {
            String passedFailedScenario = "";
            while ((passedFailedScenario = buffReaderScenario.readLine()) != null) {
                if (passedFailedScenario.trim().contains("@manual-result:")
                        || passedFailedScenario.trim().contains("#EstadoScenarioNoDefinido")) {
                    scenarios.add(passedFailedScenario);
                }
            }
        }
        return scenarios;
    }

    public static void validatePassedOrdFailed(List<String> scenarios, int numScenario) {
        String passedOrdFailed = scenarios.get(numScenario);
        String status = "";
        if (passedOrdFailed.contains("passed")) {
            status = "PASSED";
        } else if (passedOrdFailed.contains("failed")) {
            status = "FAILED";
        } else {
            status = "PENDING";
        }
        assertEquals("ESTADO SCENARIO MANUAL: ", "PASSED", status);
    }
}