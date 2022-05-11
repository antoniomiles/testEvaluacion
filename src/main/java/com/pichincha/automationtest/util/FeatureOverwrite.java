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

    private static Map<String,List<String>> currentFeatures = new HashMap<>();

    public static void overwriteFeatureFileAdd(final String featureName) throws IOException, InvalidFormatException {
        addExternalDataToFeature(featureName);
    }

    private static void addExternalDataToFeature(final String featureName) throws IOException, InvalidFormatException {
        File featureFile = new File(System.getProperty("user.dir") + "/src/test/resources/features/"+ featureName);
        final List<String> featureWithExternalData = impSetFileDataToFeature(featureFile, featureName);
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
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8)
        ) {
            for (final String writeline : currentFeatures.get(featureName)) {
                writer.write(writeline);
                writer.write("\n");
            }
        }
    }
}