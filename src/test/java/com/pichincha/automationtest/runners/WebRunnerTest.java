package com.pichincha.automationtest.runners;

import com.pichincha.automationtest.util.*;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RunWith(CustomCucumberWithSerenityRunner.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.pichincha.automationtest.hooks", "com.pichincha.automationtest.glue"},
        plugin = "json:build/cucumber-reports/json/cucumber.json",
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        tags = "not @karate and not @API and not @ManualTest and not @Mobiletest and not @smokeTest"
)

public class WebRunnerTest {

    private WebRunnerTest() {
    }

    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    private static final String EXTENSION_FEATURE = ".feature";

    @BeforeSuite
    public static void init() throws IOException {
        ControlExecution.featuresSegmentation();
        ControlExecution.setDriver();
        String featureName = variables.getProperty("featureName");
        List<String> features = FeatureOverwrite.listFilesByFolder(featureName, new File(PathConstants.featurePath()));
        for (String feature : features) {
            if (feature.contains(EXTENSION_FEATURE)) {
                FeatureOverwrite.overwriteFeatureFileAdd(feature);
            }
        }
        FeatureOverwrite.clearListFilesByFolder();
    }


    @AfterSuite
    public static void after() {
        log.info("=====> End Execution SerenityBDD");
    }
}