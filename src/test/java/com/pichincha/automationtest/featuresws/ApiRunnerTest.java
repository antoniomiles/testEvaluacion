package com.pichincha.automationtest.featuresws;

import com.intuit.karate.Runner;
import io.cucumber.junit.CucumberOptions;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.assertTrue;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

public class ApiRunnerTest {
@Test
    public void testparallel() {
    Runner.path("src/test/java/com/pichincha/automationtest/featuresws").outputCucumberJson(true).parallel(5);
    String karateOutputPath = "build/cucumber-reports";
    generateReport(karateOutputPath);
   // assertTrue(results.getErrorMessages(), results.getFailCount() == 0);

}
    public static void generateReport(String karateOutputPath) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[]{"json"},true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("build"), "Banca Movil");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }

}