package com.pichincha.automationtest.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVReader {
    private CSVReader(){}

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    private static Logger logger = Logger.getLogger(CSVReader.class.getName());


    public static List<Map<String, String>> getData(final String filePath) throws IOException {
        List<Map<String, String>> rowsData = new ArrayList<>();
        BufferedReader bfReader = null;
        String lineData = "";
        Map<Integer, String> rowHeaderPosition = new HashMap<>();
        Map<String, String> headerRow = null;
        try {
            bfReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            while ((lineData = bfReader.readLine()) != null) {

                List<String> line = parseLine(lineData);
                if(rowHeaderPosition.isEmpty()){
                    rowHeaderPosition = generateHeaderPosition(line);
                    headerRow = generateRowData(line, rowHeaderPosition);

                }else{
                    Map<String, String> rowData = generateRowData(line, rowHeaderPosition);
                    rowsData.add(rowData);
                }
            }
            if(headerRow != null) {
                rowsData.add(headerRow);
            }
        }
        finally {
            if (bfReader != null) {
                try {
                    bfReader.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING,"ERROR: ",e);
                }
            }
        }
        return rowsData;
    }

    private static Map<String, String> generateRowData(List<String> line, Map<Integer, String> rowHeaderPosition) {
        Map<String, String> result = new LinkedHashMap<>();
        for (int i = 0; i < line.size(); i++) {
            String value = line.get(i);
            result.put(rowHeaderPosition.getOrDefault(i,""), value);
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

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

}