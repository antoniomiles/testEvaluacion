package com.pichincha.automationtest.runners;

import com.pichincha.automationtest.util.*;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.util.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    private static final String WEB_DRIVER_PROPERTY = "webdriver.driver";


    public static void setDriver(){
        String envDriver = System.getenv("TIPO_DRIVER");
        if (envDriver != null && !envDriver.isEmpty()){
            System.setProperty(WEB_DRIVER_PROPERTY, envDriver);
        }
        else{
            if (variables.getProperty(WEB_DRIVER_PROPERTY).equals("${TIPO_DRIVER}")){
                System.setProperty(WEB_DRIVER_PROPERTY, "chrome");
            }
        }
    }

    @BeforeSuite
    public static void init() throws IOException {
        ControlsExecutionParallelAgents.featuresSegmentation();
        setDriver();
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
    public static void after() throws IOException {
        String featureName = variables.getProperty("featureName");
        List<String> features = FeatureOverwrite.listFilesByFolder(featureName, new File(PathConstants.featurePath()));
        for (String feature : features) {
            if (!featureName.equals(ALL_FEATURES) && !feature.endsWith(EXTENSION_FEATURE)) {
                feature += EXTENSION_FEATURE;
            }
            FeatureOverwrite.overwriteFeatureFileRemove(feature);
        }
        FeatureOverwrite.clearListFilesByFolder();
    }
}
