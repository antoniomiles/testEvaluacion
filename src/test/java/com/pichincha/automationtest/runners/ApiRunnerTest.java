package com.pichincha.automationtest.runners;

import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApiRunnerTest {
    @Test
    public void testparallel() throws IOException {
        Runner.path("src/test/resources/features/").tags("@karate").outputCucumberJson(true).parallel(5);
        String karateOutputPath = "build/karate-reports";
        generateReport(karateOutputPath);
    }

    public static void generateReport(String karateOutputPath) throws IOException {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[]{"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        JSONArray karateJson = new JSONArray();
        jsonFiles.forEach(file -> {
            karateJson.add(getReportJsonByFile(file.getAbsolutePath()));
            jsonPaths.add(file.getAbsolutePath());
        });
        String karateResumePath = "./build/karate-reports/json";
        File pathFile = new File(karateResumePath);
        if (!Files.exists(Paths.get(karateResumePath))) {
            pathFile.mkdir();
        }
        Files.write(Paths.get(karateResumePath + "/karate.json"), karateJson.toJSONString().getBytes());
        Configuration config = new Configuration(new File("build"), "Banca Movil");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }

    public static Object getReportJsonByFile(String filePath) {
        FileReader reader = null;
        try {
            reader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonObject = null;
        try {
            jsonObject = (JSONArray) jsonParser.parse(reader);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject.get(0);
    }
}