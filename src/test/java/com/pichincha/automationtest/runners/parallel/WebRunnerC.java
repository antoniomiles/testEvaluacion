package com.pichincha.automationtest.runners.parallel;

import com.pichincha.automationtest.util.AfterSuite;
import com.pichincha.automationtest.util.BeforeSuite;
import com.pichincha.automationtest.util.ControlParallel;
import com.pichincha.automationtest.util.CustomCucumberWithSerenityRunner;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;

@Slf4j
@RunWith(CustomCucumberWithSerenityRunner.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.pichincha.automationtest.hooks", "com.pichincha.automationtest.glue"},
        plugin = "json:build/cucumberreportstest/cucumberParallel3.json",
        tags = "@R3 and not @karate and not @API and not @ManualTest and not @Mobiletest and not @smokeTest"
)

public class WebRunnerC {

    private static final String RUNNER = "Runner3";
    private WebRunnerC() {
    }

    @BeforeSuite
    public static void init() {
        ControlParallel.startRunner(RUNNER);
    }

    @AfterSuite
    public static void after() {
        ControlParallel.endsRunner(RUNNER);
    }

}
