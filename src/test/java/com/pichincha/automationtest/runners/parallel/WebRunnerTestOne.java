package com.pichincha.automationtest.runners.parallel;

import com.pichincha.automationtest.util.AfterSuite;
import com.pichincha.automationtest.util.BeforeSuite;
import com.pichincha.automationtest.util.CustomCucumberWithSerenityRunner;
import com.pichincha.automationtest.util.FeatureOverwrite;
import com.pichincha.automationtest.utiltest.ControlParallelTest;
import com.pichincha.automationtest.utiltest.GenerateUnifiedReport;
import io.cucumber.junit.CucumberOptions;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

@RunWith(CustomCucumberWithSerenityRunner.class)
@CucumberOptions(
    features = "src/test/resources/features/", 
    glue = { "com.pichincha.automationtest.hooks", "com.pichincha.automationtest.glue" }, 
    plugin = "json:build/cucumberreportstest/cucumberParallel1.json", 
    tags = "@R1 and not @karate and not @ManualTest")
    
public class WebRunnerTestOne {
    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String allFeatures = "todos";

    private WebRunnerTestOne() {
    }

    @BeforeSuite
    public static void init() throws IOException, InvalidFormatException {
        String featureName = variables.getProperty("featureName");
        String[] features = getFeaturesNames(featureName);
        for (String feature : features) {
            if (!featureName.equals(allFeatures)) {
                feature += ".feature";
            }
            FeatureOverwrite.overwriteFeatureFileAdd(feature);
        }
        ControlParallelTest.setOrRemoveExecution("add");
    }

    public static String[] getFeaturesNames(String featureName) {
        String[] features;
        if (featureName.equals(allFeatures)) {
            File featureFolder = new File(System.getProperty("user.dir") + "/src/test/resources/features");
            features = featureFolder.list();
        } else {
            features = featureName.split(";");
        }
        return features;
    }

    @AfterSuite
    public static void after() throws IOException, InvalidFormatException, InterruptedException {
        while (!ControlParallelTest.validateExecution()) {
            Thread.sleep(1000);
        }
        String featureName = variables.getProperty("featureName");
        String[] features = getFeaturesNames(featureName);
        for (String feature : features) {
            if (!featureName.equals(allFeatures)) {
                feature += ".feature";
            }
            FeatureOverwrite.overwriteFeatureFileRemove(feature);
        }
        ControlParallelTest.setOrRemoveExecution("delete");
        String reportsOutputPath = "build/cucumberreportstest/";
        String jsonResumePath = "./build/cucumber-reports/json";
        String nameJsonReport = "cucumber.json";
        GenerateUnifiedReport.generateReport(reportsOutputPath, jsonResumePath, nameJsonReport);
    }
}
