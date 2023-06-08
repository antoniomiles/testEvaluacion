package com.pichincha.automationtest.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ControlExecution {

    private ControlExecution() {
        throw new IllegalStateException("Utility class");
    }

    static List<String> allFeatures = new ArrayList<>();
    static EnvironmentConfig environmentConfig = new EnvironmentConfig();
    private static boolean parameterizedSegmentation;
    private static final String WEB_DRIVER_PROPERTY = "webdriver.driver";

    public static void featuresSegmentation() {
        final String FEATURE_NAME = "todos";
        String totalAgentes = environmentConfig.getVariable("SYSTEM_TOTALJOBSINPHASE");
        String agenteNum = environmentConfig.getVariable("SYSTEM_JOBPOSITIONINPHASE");
        log.info("=====> Total Agentes: '" + totalAgentes + "' | Agente Num: '" + agenteNum + "'");
        allFeatures = FeatureOverwrite.listFilesByFolder(FEATURE_NAME, new File(PathConstants.featurePath()));

        if (valitateParalelExcecution(totalAgentes)) {
            List<String> pathsFeatureToRemove = getPathsFeatureToRemove(agenteNum);
            if (!parameterizedSegmentation) {
                pathsFeatureToRemove.clear();
                pathsFeatureToRemove = getPathsFeatureToRemoveDefault(totalAgentes, agenteNum);
            }
            removeFeatures(pathsFeatureToRemove);
        }
        FeatureOverwrite.clearListFilesByFolder();
    }

    private static boolean valitateParalelExcecution(String totalAgentes) {
        totalAgentes = totalAgentes.equals("") ? "1" : totalAgentes;
        return Integer.parseInt(totalAgentes) > 1;
    }

    private static List<String> getPathsFeatureToRemove(String agenteNum) {

        List<String> featuresToDelete = new ArrayList<>();

        for (String featurePath : allFeatures) {
            String data;
            boolean isPresentAgent = false;
            try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(featurePath), StandardCharsets.UTF_8)) {
                while ((data = bufferedReader.readLine()) != null) {
                    if (data.trim().contains(" @Agente")) parameterizedSegmentation = true;
                    isPresentAgent = data.trim().contains(" @Agente" + agenteNum);
                    if (isPresentAgent) {
                        break;
                    }
                }
                if (!isPresentAgent) {
                    featuresToDelete.add(featurePath);
                }
            } catch (IOException e) {
                throw new IllegalStateException("=====> ERROR al crear lista de features para eliminar, " + e.getMessage(), e);
            }
        }
        return featuresToDelete;
    }

    private static List<String> getPathsFeatureToRemoveDefault(String totalAgentsExecution, String agentNumberExcecution) {

        List<String> featuresPathToRemove = new ArrayList<>();
        double totalAgents = Double.parseDouble(totalAgentsExecution);
        int agentNumber = Integer.parseInt(agentNumberExcecution);
        double totalFeatures = allFeatures.size();
        double asignedFeatures = totalFeatures / totalAgents;

        double asignedFeaturesRounded = Math.round(asignedFeatures);
        log.info("=====> Total Features: " + totalFeatures + " | asignedFeatures: " + asignedFeatures + " | asignedFeaturesRounded: " + asignedFeaturesRounded);

        //solo si el num de features asignados por agente es mayor o igual a 1 continual el proceso
        if (asignedFeatures >= 1) {
            int numFeatureDesde = (int) ((agentNumber * asignedFeaturesRounded) - asignedFeaturesRounded) + 1;
            int numFeatureHasta = Math.min((int) (agentNumber * asignedFeaturesRounded), allFeatures.size());
            log.info("=====> numFeatureDesde: " + numFeatureDesde + " | numFeatureHasta: " + numFeatureHasta);

            for (int i = 0; i < allFeatures.size(); i++) {
                if (i < numFeatureDesde - 1 || i > numFeatureHasta - 1) {
                    if (agentNumber == totalAgents && numFeatureHasta < allFeatures.size() && i >= numFeatureHasta) {
                        int featuresExtras = allFeatures.size() - numFeatureHasta;
                        log.info("=====> Features Extras para Ejecutar: " + featuresExtras);
                    } else {
                        featuresPathToRemove.add(allFeatures.get(i));
                    }
                }
            }
        } else {
            throw new IllegalStateException("ERROR: NO se puede realizar segmentacion de features ");
        }
        return featuresPathToRemove;
    }

    private static void removeFeatures(List<String> pathsFeatureToRemove) {
        log.info("=====> Total de features a borrar: " + pathsFeatureToRemove.size());
        pathsFeatureToRemove.forEach(feature -> log.info("Feature a borrar:" + feature));

        for (String featurePath : pathsFeatureToRemove) {
            try {
                Files.delete(Paths.get(featurePath));
            } catch (IOException e) {
                throw new IllegalStateException("=====> ERROR al eliminar feature, " + featurePath + " - " + e.getMessage(), e);
            }
        }
    }

    public static void setDriver(){
        String envDriver = environmentConfig.getVariable("TIPO_DRIVER");
        if ( !envDriver.isEmpty()){
            System.setProperty(WEB_DRIVER_PROPERTY, envDriver);
        }
        else{
            if (environmentConfig.getVariable(WEB_DRIVER_PROPERTY).equals("${TIPO_DRIVER}")){
                System.setProperty(WEB_DRIVER_PROPERTY, "chrome");
            }
        }
    }
}
