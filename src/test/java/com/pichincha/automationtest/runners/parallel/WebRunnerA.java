package com.pichincha.automationtest.runners.parallel;

import com.pichincha.automationtest.util.*;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.awaitility.Awaitility.await;

@Slf4j
@RunWith(CustomCucumberWithSerenityRunner.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.pichincha.automationtest.hooks", "com.pichincha.automationtest.glue"},
        plugin = "json:build/cucumberreportstest/cucumberParallel1.json",
        tags = "@R1 and not @karate and not @ManualTest and not @Mobiletest"
)

public class WebRunnerA {
    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

    private WebRunnerA() {
    }

    private static final String ALL_FEATURES = "todos";
    private static final String EXTENSION_FEATURE = ".feature";
    private static final String RUNNER = "Runner1";

    @BeforeSuite
    public static void init() throws IOException {
        String featureName = variables.getProperty("featureName");
        PathConstants.featurePath();
        String[] features = getFeaturesNames(featureName);
        for (String feature : features) {
            if (!feature.contains(EXTENSION_FEATURE)) {
                feature += EXTENSION_FEATURE;
            }
            FeatureOverwrite.overwriteFeatureFileAdd(feature);
        }
        ControlParallel.setOrRemoveExecution("add");
        log.info("Inicia " + RUNNER);
    }

    public static String[] getFeaturesNames(String featureName) {
        String[] features;
        if (featureName.equalsIgnoreCase(ALL_FEATURES)) {
            File featureFolder = new File(PathConstants.featurePath());
            features = featureFolder.list();
        } else {
            features = featureName.split(";");
        }
        return features;
    }

    @AfterSuite
    public static void after() throws IOException {
        await().atMost(60, MINUTES).until(featuresOverwritten());
        String featureName = variables.getProperty("featureName");
        String[] features = getFeaturesNames(featureName);
        for (String feature : features) {
            if (!featureName.equals(ALL_FEATURES)) {
                feature += EXTENSION_FEATURE;
            }
            FeatureOverwrite.overwriteFeatureFileRemove(feature);
        }
        log.info("Termina Runner1");
        ControlParallel.setOrRemoveExecution("delete");
        String reportsOutputPath = "build/cucumberreportstest/";
        String jsonResumePath = "./build/cucumber-reports/json";
        String nameJsonReport = "cucumber.json";
        GenerateUnifiedReport.generateReport(reportsOutputPath, jsonResumePath, nameJsonReport);
    }

    private static Callable<Boolean> featuresOverwritten() {
        return () -> ControlParallel.validateExecution(RUNNER);
    }
}