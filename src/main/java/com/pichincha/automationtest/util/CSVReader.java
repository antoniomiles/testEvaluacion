package com.pichincha.automationtest.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CSVReader {
    private CSVReader() {
    }

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public static List<Map<String, String>> getData(final String filePath) throws IOException {
        List<Map<String, String>> rowsData = new ArrayList<>();
        String lineData = "";
        Map<Integer, String> rowHeaderPosition = new HashMap<>();
        Map<String, String> headerRow = null;
        try (BufferedReader bfReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));) {
            while ((lineData = bfReader.readLine()) != null) {

                List<String> line = parseLine(lineData);
                if (rowHeaderPosition.isEmpty()) {
                    rowHeaderPosition = generateHeaderPosition(line);
                    headerRow = generateRowData(line, rowHeaderPosition);

                } else {
                    Map<String, String> rowData = generateRowData(line, rowHeaderPosition);
                    rowsData.add(rowData);
                }
            }
            if (headerRow != null) {
                rowsData.add(headerRow);
            }
        }
        return rowsData;
    }

    private static Map<String, String> generateRowData(List<String> line, Map<Integer, String> rowHeaderPosition) {
        Map<String, String> result = new LinkedHashMap<>();
        for (int i = 0; i < line.size(); i++) {
            String value = line.get(i);
            result.put(rowHeaderPosition.getOrDefault(i, ""), value);
        }
        return result;
    }

    private static Map<Integer, String> generateHeaderPosition(List<String> line) {
        Map<Integer, String> result = new HashMap<>();
        for (int i = 0; i < line.size(); i++) {
            result.put(i, line.get(i));
        }
        return result;
    }

    public static List<String> parseLine(String csvLine) {
        return parseLine(csvLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String csvLine, char separators) {
        return parseLine(csvLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String csvLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        // if empty, return!
        if (csvLine.isEmpty())
            return result;
        if (customQuote == ' ')
            customQuote = DEFAULT_QUOTE;
        if (separators == ' ')
            separators = DEFAULT_SEPARATOR;

        StringBuilder separateWords = new StringBuilder();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = csvLine.toCharArray();

        for (char ch : chars) {
            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    // Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        separateWords.append(ch);
                    }
                }
            } else {
                if (ch == '\n')
                    break;
                if (ch != '\r') {
                    if (ch == customQuote) {
                        inQuotes = true;
                        // Fixed : allow "" in empty quote enclosed
                        if (chars[0] != '"' && customQuote == '\"') {
                            separateWords.append(ch);
                        }
                        // double quotes in column will hit this!
                        if (startCollectChar) {
                            separateWords.append(ch);
                        }
                    } else if (ch == separators) {
                        result.add(separateWords.toString());
                        separateWords.setLength(0);
                        startCollectChar = false;
                    } else {
                        separateWords.append(ch);
                    }
                }
            }
        }
        result.add(separateWords.toString());
        return result;
    }

}