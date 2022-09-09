package com.pichincha.automationtest.runners.parallel;

import com.pichincha.automationtest.util.AfterSuite;
import com.pichincha.automationtest.util.BeforeSuite;
import com.pichincha.automationtest.util.CustomCucumberWithSerenityRunner;
import com.pichincha.automationtest.utiltest.ControlParallelTest;
import io.cucumber.junit.CucumberOptions;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(CustomCucumberWithSerenityRunner.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.pichincha.automationtest.hooks", "com.pichincha.automationtest.glue"},
        plugin = "json:build/cucumberreportstest/cucumberParallel3.json",
        tags = "@R3 and not @karate and not @ManualTest"
)
public class WebRunnerTestThree {
    private WebRunnerTestThree(){}
    @BeforeSuite
    public static void init() throws InterruptedException, IOException, InvalidFormatException {
        Thread.sleep(2000);
        ControlParallelTest.setOrRemoveExecution("add");
    }
    @AfterSuite
    public static void after() throws IOException, InvalidFormatException {
        ControlParallelTest.setOrRemoveExecution("delete");
    }
}
