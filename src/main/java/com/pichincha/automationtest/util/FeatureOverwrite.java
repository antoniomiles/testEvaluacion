package com.pichincha.automationtest.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FeatureOverwrite {
    static PropertiesReader readProperties= new PropertiesReader();

    private static Map<String,List<String>> currentFeatures = new HashMap<>();

    public static void overwriteFeatureFileAdd(final String featureName) throws IOException, InvalidFormatException {
        addExternalDataToFeature(featureName);
    }

    private static void addExternalDataToFeature(final String featureName) throws IOException, InvalidFormatException {
        File featureFile = new File(System.getProperty("user.dir") + "/src/test/resources/features/"+ featureName);
        List<String> featureWithExternalData=null;
        if(featureName.contains("Manual")){
            featureWithExternalData= impSetPaneOrCsvDataToFeature(featureFile);
        }else{
            featureWithExternalData= impSetFileDataToFeature(featureFile, featureName);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8)
        ) {
            for (final String writeline : featureWithExternalData) {
                writer.write(writeline);
                writer.write("\n");
            }
        }
    }

    private static List<String> impSetFileDataToFeature(final File featureFile, String featureName) throws IOException {
        final List<String> fileData = new ArrayList<String>();
        try (BufferedReader buffReader = Files.newBufferedReader(
                Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8)
        ) {
            String data;
            List<String> previousData = new ArrayList<String>();
            boolean exampleData = false;
            while ((data = buffReader.readLine()) != null) {
                previousData.add(data);
                if (data.trim().contains("@externaldata")) {
                    String filePath = getValidFilePath(data);
                    List<Map<String, String>> externalData = getDataFromFile(filePath);
                    Collection<String> headers = externalData.get(0).keySet();
                    fileData.add(getGherkinExample(headers));
                    for (int rowNumber = 0; rowNumber < externalData.size() - 1; rowNumber++) {
                        Collection<String> rowValues = externalData.get(rowNumber).values();
                        fileData.add(getGherkinExample(rowValues));
                    }
                    exampleData = false;
                    data="#"+data;
                }
                if (!exampleData) {
                    fileData.add(data);
                }
                if (data.contains("Examples")){
                    exampleData=true;
                }
            }
            currentFeatures.put(featureName,previousData);
        }
        return fileData;
    }

    private static String getValidFilePath(String data) {
        return data.substring(StringUtils.ordinalIndexOf(data, "@", 2)+1)
                .replace("|","")
                .trim()
                .toLowerCase();
    }

    private static List<Map<String, String>> getDataFromFile(String filePath) throws IOException {
        if (isCSV(filePath)) return CSVReader.getData(filePath);
        return new ArrayList<>();
    }

    private static boolean isCSV(String filePath) {
        return filePath
                .endsWith(".csv");
    }

    private static String getGherkinExample(Collection<String> examplesFields){
        String example = "";
        for (String field : examplesFields) {
            example = String.format("%s|%s", example, field);
        }
        return example + "|";
    }

    public static void overwriteFeatureFileRemove(final String featureName) throws IOException, InvalidFormatException {
        removeExternalDataToFeature(featureName);
    }

    private static void removeExternalDataToFeature(final String featureName) throws IOException {
        File featureFile = new File(System.getProperty("user.dir") + "/src/test/resources/features/"+ featureName);

        final List<String> featureWithExternalData;

        if(featureName.contains("Manual")){
            featureWithExternalData= impRemovePaneDataToFeature(featureFile);
        }else{
            featureWithExternalData= currentFeatures.get(featureName);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8)
        ) {
            for (final String writeline : featureWithExternalData) {
                writer.write(writeline);
                writer.write("\n");
            }
        }
    }

    private static List<String> impSetPaneOrCsvDataToFeature(final File featureFile) throws IOException, InvalidFormatException {
        final List<String> fileData = new ArrayList<String>();
        BufferedReader buffReaderScenario=null;
        BufferedReader buffReader = null;
        try {
            buffReader = Files.newBufferedReader(Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8);
            buffReaderScenario = Files.newBufferedReader(Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8);
            String data;
            String externalDataSt ="";
            final List<String> snarios = new ArrayList<String>();
            String nameScenario ="";
            int numScenario = 0;
            String azureOrLocalExecution=readProperties.getPropiedad("azure.or.local.execution");
            boolean foundHashTag = false;
            while ((nameScenario = buffReaderScenario.readLine()) != null) {
                if (nameScenario.trim().contains("Scenario:")) {
                    snarios.add(nameScenario);
                }
            }
            while ((data = buffReader.readLine()) != null) {
                if (data.trim().contains("@manual-result:")) {
                    foundHashTag = false;
                }
                if (data.trim().contains("@manual")) {
                    foundHashTag = true;
                    //fileData.add(data);
                }
                if (foundHashTag) {
                    if (azureOrLocalExecution.equalsIgnoreCase("azure")){
                        externalDataSt = ManualReadFeature.setPassedOrFailedFromCSV(snarios.get(numScenario),numScenario,readProperties.getPropiedad("path.data.passed.or.failed"));
                    }else{
                        externalDataSt = ManualReadFeature.setPassedOrFailedFromPane(snarios.get(numScenario),numScenario);
                    }
                    numScenario++;
                    fileData.add(data+" "+externalDataSt.trim());
                    foundHashTag = false;
                    continue;
                }
                fileData.add(data);
            }
        }
        finally {
            if (buffReader != null) {
                try {
                    buffReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (buffReaderScenario != null) {
                try {
                    buffReaderScenario.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileData;
    }

    private static List<String> impRemovePaneDataToFeature(final File featureFile) throws IOException {
        final List<String> fileData = new ArrayList<String>();
        BufferedReader buffReader=null;
        try{
            buffReader = Files.newBufferedReader(Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8);
            String data;
            boolean foundHashTag = false;
            while ((data = buffReader.readLine()) != null) {
                if (data.trim().contains("@manual-result:") ||data.trim().contains("#EstadoScenarioNoDefinido") ) {
                    data = data.replace(" @manual-result:passed","");
                    data = data.replace(" @manual-result:failed","");
                    data = data.replace(" #EstadoScenarioNoDefinido","");
                        fileData.add(data);
                        continue;
                }
                fileData.add(data);
            }
        }finally {
            if (buffReader != null) {
                try {
                    buffReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileData;
    }

}