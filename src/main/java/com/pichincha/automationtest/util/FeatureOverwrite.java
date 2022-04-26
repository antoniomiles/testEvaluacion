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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FeatureOverwrite {

    public static void overwriteFeatureFileAdd(final String featureDirectoryPath) throws IOException, InvalidFormatException {
        addExternalDataToFeature(new File(featureDirectoryPath));
    }

    public static void overwriteFeatureFileRemove(final String featuresDirectoryPath) throws IOException, InvalidFormatException {
        removeExternalDataToFeature(new File(featuresDirectoryPath));
    }

    private static void addExternalDataToFeature(final File featureFile) throws IOException, InvalidFormatException {
        final List<String> featureWithExternalData= impSetFileDataToFeature(featureFile);
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8)
        ) {
            for (final String writeline : featureWithExternalData) {
                writer.write(writeline);
                writer.write("\n");
            }
        }
    }

    private static void removeExternalDataToFeature(final File featureFile) throws IOException, InvalidFormatException {
        final List<String> featureWithExcelData = impRemoveFileDataToFeature(featureFile);
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8)
        ) {
            for (final String writeline : featureWithExcelData) {
                writer.write(writeline);
                writer.write("\n");
            }
        }
    }

    private static List<String> impSetFileDataToFeature(final File featureFile) throws IOException, InvalidFormatException {
        final List<String> fileData = new ArrayList<String>();
        try (BufferedReader buffReader = Files.newBufferedReader(
                Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8)
        ) {
            String data;
            List<Map<String, String>> externalData = null;
            boolean foundHashTag = false;
            boolean featureData = false;
            boolean isCsv = false;
            while ((data = buffReader.readLine()) != null) {
                String sheetName = null;
                String filePath = null;
                if (data.trim().contains("##end")) {
                    externalData = null;
                    foundHashTag = false;
                    featureData = false;
                }
                if (data.trim().contains("##@externaldata")) {
                    isCsv = data.toLowerCase().trim().endsWith(".csv");
                    if(isCsv){
                        filePath = data.substring(StringUtils.ordinalIndexOf(data, "@", 2) + 1);
                    }else{
                        filePath = data.substring(StringUtils.ordinalIndexOf(data, "@", 2) + 1, data.lastIndexOf('@'));
                        sheetName = data.substring(data.lastIndexOf('@') + 1);
                    }
                    foundHashTag = true;
                    fileData.add(data);
                }
                if (foundHashTag) {
                    if(isCsv){
                        externalData = CSVReader.getData(filePath);
                    }else {
                       // externalData = new ExcelReader().getData(filePath, sheetName);
                    }
                    for (int rowNumber = 0; rowNumber < externalData.size() - 1; rowNumber++) {
                        String cellData = "";
                        for (final Entry<String, String> mapData : externalData.get(rowNumber).entrySet()) {
                            cellData = String.format("%s|%s", cellData, mapData.getValue());
                        }
                        fileData.add(cellData + "|");
                    }
                    foundHashTag = false;
                    featureData = true;
                    continue;
                }
                if (data.length()>0 && ('|' == data.charAt(0) || data.endsWith("|"))) {
                    if (featureData) {
                        continue;
                    } else {
                        fileData.add(data);
                        continue;
                    }
                } else {
                    featureData = false;
                }
                fileData.add(data);
            }
        }
        return fileData;
    }


    private static List<String> impRemoveFileDataToFeature(final File featureFile) throws IOException {
        final List<String> fileData = new ArrayList<String>();
        try (BufferedReader buffReader = Files.newBufferedReader(
                Paths.get(featureFile.getAbsolutePath()), StandardCharsets.UTF_8)
        ) {
            String data;
            boolean foundHashTag = false;
            while ((data = buffReader.readLine()) != null) {
                if (data.trim().contains("##end")) {
                    foundHashTag = false;
                }
                if (data.trim().contains("##@externaldata")) {
                    foundHashTag = true;
                }
                if (data.length()>0 && ('|' == data.charAt(0) || data.endsWith("|"))) {
                    if (foundHashTag) {
                        continue;
                    } else {
                        fileData.add(data);
                        continue;
                    }
                }
                fileData.add(data);
            }
        }
        return fileData;
    }


}