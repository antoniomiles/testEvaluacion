package com.pichincha.automationtest.runners;

import com.pichincha.automationtest.util.*;
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
        glue = {"com.pichincha.automationtest.hooks", "com.pichincha.automationtest.glue"},
        plugin = "json:build/cucumber-reports/json/cucumber.json",
        tags = "not @karate and not @ManualTest"
)
public class WebRunnerTest {

    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

    @BeforeSuite
    public static void init() throws IOException {
        String featureName = variables.getProperty("featureName");
        PathConstants.featurePath();
        String[] features = getFeaturesNames(featureName);
        for (String feature : features) {
            if (!featureName.equals("todos")) {
                feature += ".feature";
            }
            FeatureOverwrite.overwriteFeatureFileAdd(feature);
        }
    }

    public static String[] getFeaturesNames(String featureName) {
        String[] features;
        if (featureName.equalsIgnoreCase("todos")) {
            File featureFolder = new File(PathConstants.featurePath());
            features = featureFolder.list();
        } else {
            features = featureName.split(";");
        }
        return features;
    }

    @AfterSuite
    public static void after() throws IOException, InvalidFormatException {
        String featureName = variables.getProperty("featureName");
        String[] features = getFeaturesNames(featureName);
        for (String feature : features) {
            if (!featureName.equals("todos")) {
                feature += ".feature";
            }
            FeatureOverwrite.overwriteFeatureFileRemove(feature);
        }
    }
}
