package com.pichincha.automationtest.runners;

import com.pichincha.automationtest.util.*;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.util.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RunWith(CustomCucumberWithSerenityRunner.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.pichincha.automationtest.hooks", "com.pichincha.automationtest.glue"},
        plugin = "json:build/cucumber-reports/json/cucumber.json",
        tags = "not @karate and not @ManualTest and not @Mobiletest"
)

public class WebRunnerTest {
    private static final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

    private WebRunnerTest() {
    }

    private static final String ALL_FEATURES = "todos";
    private static final String EXTENSION_FEATURE = ".feature";
    static List<String> featuresList = new ArrayList<>();

    @BeforeSuite
    public static void init() throws IOException {
        String featureName = variables.getProperty("featureName");
        List<String> features = listFilesByFolder(featureName, new File(PathConstants.featurePath()));
        for (String feature : features) {
            if (feature.contains(EXTENSION_FEATURE)) {
                FeatureOverwrite.overwriteFeatureFileAdd(feature);
            }
        }
    }

    public static List<String> listFilesByFolder(String featureName, final File folder) {
        if (featureName.equalsIgnoreCase(ALL_FEATURES)) {
            for (final File fileOrFolder : Objects.requireNonNull(folder.listFiles())) {
                if (fileOrFolder.isDirectory()) {
                    listFilesByFolder(featureName, fileOrFolder);
                } else {
                    featuresList.add(fileOrFolder.getAbsolutePath());
                }
            }
        } else {
            featuresList = List.of(featureName.split(";"));
        }
        return featuresList;
    }

    @AfterSuite
    public static void after() throws IOException {
        String featureName = variables.getProperty("featureName");
        List<String> features = listFilesByFolder(featureName, new File(PathConstants.featurePath()));
        for (String feature : features) {
            if (!featureName.equals(ALL_FEATURES) && !feature.endsWith(EXTENSION_FEATURE)) {
                feature += EXTENSION_FEATURE;
            }
            FeatureOverwrite.overwriteFeatureFileRemove(feature);
        }
    }
}
